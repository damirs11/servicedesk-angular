/**
 * Модуль для работы с модальными окнами. Являет собой обертку над модальными окнами библиотеки Bootstrap.
 */
(function () {
    'use strict';

    angular
        .module('ui.dialogs', [
            'pascalprecht.translate',
            'ui.bootstrap.modal'
            //'ngMessages'
        ])
        /**
         * Варианты ответов диалоговых окон. Например, для окна с вопросом "Да\Нет"
         */
        .constant('modalResult', {
            yes: 'yes',
            no: 'no',
            cancel: 'cancel',
            ok: 'ok'
        })
        .factory('uiDialogs', ['$uibModal', '$translate',
            function ($uibModal, $translate) {
                /** Относительный путь для папки с шаблонами модальных форм */
                var relativePath = 'js/app/widget/modal/';

                /**
                 * Проверяет наличие значения параметра, и если нет, то вставляет значение по умолчанию
                 * @param param проверяемое значение
                 * @param defaultValue значение по умолчанию, если основное значение не определено
                 * @returns {string} значение парамеетра
                 */
                function _getParamValue(param, defaultValue) {
                    return angular.isDefined(param) ? angular.copy(param) : $translate.instant(defaultValue)
                }

                return {
                    /**
                     * Сообщение об ошибке
                     *
                     * @param header заголовок
                     * @param msg текст сообщения
                     */
                    error: function (header, msg) {
                        return $uibModal.open({
                            templateUrl: relativePath + 'simple.html',
                            controller: 'simpleDialogCtrl',
                            backdrop: 'static',
                            resolve: {
                                data: function () {
                                    return {
                                        header: _getParamValue(header, 'DIALOGS_ERROR'),
                                        msg:    _getParamValue(msg, ''),
                                        style:  'dialog-header-error'
                                    };
                                }
                            }
                        })
                    },
                    /**
                     * Уведомление
                     *
                     * @param header заголовок
                     * @param msg текст сообщения
                     */
                    notify: function (header, msg) {
                        return $uibModal.open({
                            templateUrl: relativePath + 'simple.html',
                            controller: 'simpleDialogCtrl',
                            backdrop: 'static',
                            resolve: {
                                data: function () {
                                    return {
                                        header: _getParamValue(header, 'DIALOGS_NOTIFICATION'),
                                        msg:    _getParamValue(msg, ''),
                                        style:  'dialog-header-notify'
                                    };
                                }
                            }
                        })
                    },
                    /**
                     * Универсальный диалог вопрос-ответ, можно задавать названия кнопок в opts
                     * opts.labelYes - название кнопки "Да"
                     * opts.labelNo - название кнопки "Нет"
                     * opts.labelClose - название кнопки "Закрыть"
                     *
                     * @param header строка, заголовок
                     * @param msg строка, текст сообщения
                     * @param opts объект, параметры диалогового окна
                     */
                    confirm: function (header, msg, opts) {
                        var opts = angular.extend({}, opts);
                        return $uibModal.open({
                            templateUrl: relativePath + 'confirm.html',
                            controller: 'confirmDialogCtrl',
                            backdrop: 'static',
                            resolve: {
                                data: function () {
                                    return {
                                        header:     _getParamValue(header, 'DIALOGS_CONFIRMATION'),
                                        msg:        _getParamValue(msg, 'DIALOGS_CONFIRMATION_MSG'),
                                        labelYes:   _getParamValue(opts.labelYes, 'DIALOGS_YES'),
                                        labelNo:    _getParamValue(opts.labelNo, 'DIALOGS_NO'),
                                        labelClose: _getParamValue(opts.labelClose, 'DIALOGS_CLOSE')
                                    };
                                }
                            }
                        })
                    },
                    /**
                     * Универсальный диалог вопрос-ответ, можно задавать названия кнопок в opts
                     * opts.labelOk - название кнопки "Применить"
                     * opts.labelClose - название кнопки "Закрыть"
                     *
                     * @param header строка, заголовок
                     * @param msg строка, текст сообщения
                     * @param opts объект, параметры диалогового окна
                     */
                    inputMessage: function (header, msg, opts) {
                        var opts = angular.extend({}, opts);
                        return $uibModal.open({
                            templateUrl: relativePath + 'input-message.html',
                            controller: 'inputMessageDialogCtrl',
                            backdrop: 'static',
                            resolve: {
                                data: function () {
                                    return {
                                        header:     _getParamValue(header, 'DIALOGS_INPUT_TEXT'),
                                        msg:        _getParamValue(msg, 'DIALOGS_INPUT_TEXT_MSG'),
                                        labelOk:    _getParamValue(opts.labelOk, 'DIALOGS_OK'),
                                        labelClose: _getParamValue(opts.labelClose, 'DIALOGS_CLOSE')
                                    };
                                }
                            }
                        })
                    }

                }
            }])
        /**
         * Простой контроллер для модальных окон с единственной кнопкой "Закрыть"
         */
        .controller('simpleDialogCtrl', ['$scope', '$uibModalInstance', 'data', 'modalResult',
            function ($scope, $uibModalInstance, data, modalResult) {
                $scope.header = data.header;
                $scope.msg = data.msg;
                $scope.dialogStyle = data.style;
                $scope.close = function () {
                    $uibModalInstance.close(modalResult.ok);
                    $scope.$destroy();
                };
            }])
        /**
         * Контроллер для диалогового окна выбора
         */
        .controller('confirmDialogCtrl', ['$scope', '$uibModalInstance', 'data', 'modalResult',
            function ($scope, $uibModalInstance, data, modalResult) {
                $scope.header = data.header;
                $scope.msg = data.msg;
                $scope.labelYes = data.labelYes;
                $scope.labelNo = data.labelNo;
                $scope.labelClose = data.labelClose;
                $scope.yes = function () {
                    $uibModalInstance.close(modalResult.yes);
                    $scope.$destroy();
                };
                $scope.no = function () {
                    $uibModalInstance.close(modalResult.no);
                    $scope.$destroy();
                };
                $scope.close = function () {
                    $uibModalInstance.dismiss(modalResult.cancel);
                    $scope.$destroy();
                };
            }])
        /**
         * Контроллер для ввода строки
         */
        .controller('inputMessageDialogCtrl', ['$scope', '$uibModalInstance', 'data', 'modalResult',
            function ($scope, $uibModalInstance, data, modalResult) {
                $scope.showError = false; // отображать или нет ошибки ввода значений
                $scope.header = data.header;
                $scope.msg = data.msg;
                $scope.labelOk = data.labelOk;
                $scope.labelClose = data.labelClose;
                $scope.inputMessage = '';
                $scope.ok = function () {
                    $scope.showError = (this.inputMessageForm.$dirty && this.inputMessageForm.$invalid) || this.inputMessageForm.$pristine;
                    if ($scope.showError) {
                        return; // отображаем сообщения об ошибках и не отправляем запрос на сервер
                    }
                    $uibModalInstance.close($scope.inputMessage);
                    $scope.$destroy();
                };
                $scope.close = function () {
                    $uibModalInstance.dismiss(modalResult.cancel);
                    $scope.$destroy();
                };
                $scope.hitEnter = function (evt) {
                    if (angular.equals(evt.keyCode, 13))
                        $scope.ok();
                }
            }]);
}());