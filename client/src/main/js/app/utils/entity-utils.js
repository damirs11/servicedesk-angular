(function () {
    'use strict';

    angular
        .module('utils.entity', [
            'ui.grid'
        ])
        .factory('appEntityUtils', ['$log', '$window', '$http',
            function ($log, $window, $http) {
                var commonRestUrl = "rest/entity/"; //базовая ссылка на CommonRestController
                /**
                 * Возвращает список идентификаторов объектов в виде строки. Разделитель - запятая
                 *
                 * @param entities список объектов
                 * @returns {string} список идентификаторов, разделенных запятой
                 */
                function getEntityIds(entities) {
                    var result = [];
                    entities.forEach(function (entity) {
                        result.push(entity.id);
                    });
                    return result.join(", ");
                }

                /**
                 * Возвращает для таблицы список идентификаторов объектов в выделенных строках
                 *
                 * @param gridApi данные о таблице
                 * @returns {string} список идентификаторов, разделенных запятой
                 */
                function getSelectedEntityIds(gridApi) {
                    if (gridApi.selection != undefined) {
                        return getEntityIds(gridApi.selection.getSelectedRows());
                    } else {
                        return "";
                    }
                }

                /**
                 * Служебная функция для отправки запроса на получения списков сущностей с сервера и заполнения им переданного массива.
                 * @param url адрес REST-сервиса для получения списка
                 * @param scope объект, к которому нужно добавить массив полученных сущностей
                 * @param list строка, содержащая название массива сущностей
                 * @param params дополнительные параметры в запрос (не обязательный)
                 * @returns {promise} полученный в ходе работы метод промис. Нужен, чтобы можно было организовывать цепочки вызовов.
                 */
                function getEntityList(url, scope, list, params) {
                    var promise;
                    if (params && params != null) {
                        promise = $http.get(url, {params: params});
                    } else {
                        promise = $http.get(url);
                    }
                    var result = promise
                        .then(function (response) {
                            if (response.data) {
                                scope[list] = response.data.list || response.data || [];
                            }
                        });
                    return result;
                }

                /**
                 * Функция для получения параметров сортировки в запросах на извлечение данных с сервера
                 * @param scope объект, содержащиё параметры сортировки. Как правило - $scope.dataOptions
                 * @returns {string}
                 */
                function getSort(scope) {
                    // Собираем параметры сортировки
                    var sort = "";
                    if (scope.sort && scope.sort.length != 0) {
                        sort = scope.sort.reduce(function (str, column) {
                            str += column.field + '-' + column.sort.direction + ';';
                            return str;
                        }, "");
                    }
                    return sort;
                }

                /**
                 * Функция для построения базовых параметров запроса на выбюорку данных
                 * @param scope объект, содержащиё параметры запроса. Как правило - $scope.dataOptions
                 * @returns {{paging: string, sort: string}}
                 */
                function getRequestParams(dataOptions) {
                    var params = {};
                    //Формируем параметры
                    if (dataOptions.paging) {
                        // Параметры пейджинга
                        var from = dataOptions.paging.pageSize * (dataOptions.paging.pageNumber - 1) + 1;
                        var to = from + dataOptions.paging.pageSize - 1;
                        params.paging = from + ';' + to;
                    }
                    if (dataOptions.sort) {
                        // Параметры сортировки
                        params.sort = getSort(dataOptions);
                    }
                }

                /**
                 * Функция для очистки выделения в таблице
                 * @param gridApi ссылка на API таблицы (как правило, $scope.gridApi)
                 */
                function clearGridSelection(gridApi) {
                    gridApi.selection.clearSelectedRows();
                }

                /**
                 * Функция для поиска в массиве строк, пришедших с сервера, строки, соответствующей той, что выделена в таблице
                 * @param data массив строк с сервера
                 * @param view выделенная строка
                 * @returns {*} строка, соответствующая выделенной.
                 */
                function getDataByView(data, view) {
                    return data.reduce(function (res, item) {
                        if (item.id == view.id) {
                            res = item;
                        }
                        return res; //есди совпало, возвращаем то, что совпало, иначе - то, что пришло.
                    }, {});
                } //Поиск выделенной строки в массиве строк, поступившем с сервера. Ищем по id, возвращаем последнюю найденную.

                /**
                 * Функция для обновления текущей строки таблицы
                 * @param lastSelectedRow выделенная строка
                 * @param scope ссылка на $scope контроллера, из которого вызываем функцию
                 * @param callback функция, которую нужно вызвать после обновления текущей строки (не обязательный)
                 */
                function setCurrentRow(lastSelectedRow, scope, callback) {
                    var gridApi;
                    if (scope.gridApi) {
                        gridApi = scope.gridApi;
                    } else {
                        return;
                    }
                    if ((lastSelectedRow && !lastSelectedRow.isSelected) || gridApi.grid.selection.selectedCount != 1) { //если всего одна строка выделена - можем редактировать.
                        //Строки не выделены, или выделены все
                        scope.editMode = false;
                        scope.currentEntityView = {};
                        scope.tempEntityData = {};
                        scope.tempEntityView = {};
                    } else {
                        //Выделена 1 строка
                        scope.editMode = true;
                        scope.currentEntityView = lastSelectedRow.entity;
                        var currentEntityData = getDataByView(scope.dataOptions.data, scope.currentEntityView);
                        scope.tempEntityData = scope.sendEntityData = angular.extend({}, currentEntityData);
                        scope.tempEntityView = angular.extend({}, scope.currentEntityView);
                    }

                    if (gridApi.grid.selection.selectedCount > 0) {
                        saveSelection(scope);
                    }

                    //Действия, которые нужно выполнить после обновления текущей строки, например, переопределить доступность кнопок.
                    if (callback && (typeof callback) == "function") {
                        callback();
                    }
                }

                /**
                 * Возвращает список объектов, соответствующих выделенным строкам в таблице
                 * @param scope
                 * @returns {Array}
                 */
                function getSelectedEntities(scope) {
                    var result = [];
                    if (scope.gridApi.selection != undefined) {
                        scope.gridApi.selection.getSelectedRows().forEach(function (row) {
                            scope.dataOptions.data.forEach(function (entity) {
                                if (row.id == entity.id) {
                                    result.push(entity);
                                }
                            });
                        });
                    }
                    return result;
                }

                return {
                    commonRestUrl: commonRestUrl,
                    getEntityIds: getEntityIds,
                    getSelectedEntityIds: getSelectedEntityIds,
                    getEntityList: getEntityList,
                    getSort: getSort,
                    getRequestParams: getRequestParams,
                    clearGridSelection: clearGridSelection,
                    getDataByView: getDataByView,
                    setCurrentRow: setCurrentRow,
                    getSelectedEntities: getSelectedEntities
                };
            }])
}());