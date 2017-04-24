// Документацию по параметрам диалогов смотри на странице
// https://github.com/m-e-conroy/angular-dialog-service, либо
// в исходных кодах модуля "dialogs.main"
(function () {
    'use strict';

    angular
        .module('ui.dialogs', [
            'ui.bootstrap',
            'ui.bootstrap.modal',
            'ngSanitize',
            'dialogs.main',
            'ngMessages'
        ])
        /**
         * Варианты ответов диалоговых окон. Например, для окна с вопросом "Да\Нет"
         */
        .constant('dialogResult', {
            yes: 'yes',
            no: 'no',
            canceled: 'canceled'
        })
        .provider('uiDialogs', ['$uibModal', '$log', 'dialogs', '$translate', 'dialogResult',
            function ($uibModal, $log, dialogs, $translate, dialogResult) {
               this.$get = function ($uibModal, $log, dialogs, $translate, dialogResult) {

               // Настройки по умолчанию. См. http://angular-ui.github.io/bootstrap/#/modal
               var _wSize = 'md';
               var _b = 'static';

                var _setOpts = function (opts) {
                    var _opts = {};
                    opts = opts || {};
                    _opts.size = (angular.isDefined(opts.size) && ((opts.size === 'sm') || (opts.size === 'lg') || (opts.size === 'md'))) ? opts.size : _wSize;
                    _opts.backdrop = (angular.isDefined(opts.backdrop)) ? opts.backdrop : _b;
                    return _opts;
                };

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
                     * @param header строка, заголовок
                     * @param msg строка, текст сообщения
                     * @param opts объект, параметры диалогового окна
                     */
                    error: function (header, msg, opts) {
                        opts = _setOpts(opts);
                        return dialogs.error(header, msg, opts)
                    },
                    /**
                     * Диалог-ожидание.
                     *
                     * @param header строка, заголовок
                     * @param msg строка, текст сообщения
                     * @param progress число, процент завершения, может быть функцией
                     * @param opts объект, параметры диалогового окна
                     */
                    wait: function (header, msg, progress, opts) {
                        opts = _setOpts(opts);
                        return dialogs.wait(header, msg, progress, opts)
                    },
                    /**
                     * Диалог-уведомление. Сообщает пользователю информацию.
                     *
                     * @param header строка, заголовок
                     * @param msg строка, текст сообщения
                     * @param opts объект, параметры диалогового окна
                     */
                    notify: function (header, msg, opts) {
                        opts = _setOpts(opts);
                        return dialogs.notify(header, msg, opts)
                    },
                    /**
                     * Создает произвольный диалог
                     *
                     * @param url строка, адреса html-макета
                     * @param ctrlr строка, название контроллера
                     * @param data объект, данные для передачи в контроллер
                     * @param opts объект, параметры диалогового окна
                     */
                    create: function (url, ctrlr, data, opts, ctrlAs) {
                        opts = _setOpts(opts);
                        return dialogs.create(url, ctrlr, data, opts, ctrlAs)
                    },
                    /**
                     * Универсальный диалог вопрос-ответ, можно задавать названия кнопок в opts
                     * opts.labelYes - название кнопки "Да"
                     * opts.labelNo - название кнопки "Нет"
                     *
                     * @param header строка, заголовок
                     * @param msg строка, текст сообщения
                     * @param opts объект, параметры диалогового окна
                     */
                    confirm: function (header, msg, opts) {
                        opts = _setOpts(opts);
                        var data = {
                            header: _getParamValue(header, 'DIALOGS_CONFIRMATION'),
                            msg: _getParamValue(msg, 'DIALOGS_CONFIRMATION_MSG'),
                            labelYes: _getParamValue(opts.labelYes, 'DIALOGS_YES'),
                            labelNo: _getParamValue(opts.labelNo, 'DIALOGS_NO'),
                            labelCancel: _getParamValue(opts.labelCancel, 'DIALOGS_CANCEL')
                        };
                        return dialogs.create('js/app/widget/dialog/confirm.html', 'confirmCtrl', data, opts)
                    },
                    /**
                     * Диалог для ввода строки
                     *
                     * @param header строка, заголовок
                     * @param msg строка, текст сообщения
                     * @params opts объект, параметры диалогового окна
                     *
                     * @returns строка
                     */
                    inputMessage: function (header, msg, opts) {
                        opts = _setOpts(opts);
                        var data = {
                            header: _getParamValue(header, 'DIALOGS_INPUT_TEXT'),
                            msg: _getParamValue(msg, 'DIALOGS_INPUT_TEXT_MSG'),
                            labelYes : _getParamValue(opts.labelYes, 'DIALOGS_YES'),
                            labelCancel: _getParamValue(opts.labelCancel, 'DIALOGS_CANCEL')
                        };
                        return dialogs.create('js/common/dialogs/input-message.html', 'inputMessageCtrl', data, opts)
                    }
                }
            }
        }])
        .controller('confirmCtrl', ['$scope', '$uibModalInstance', 'data',
            function ($scope, $uibModalInstance, data) {
                $scope.header = data.header;
                $scope.msg = data.msg;
                $scope.labelYes = data.labelYes;
                $scope.labelNo = data.labelNo;
                $scope.labelCancel = data.labelCancel;
                $scope.no = function () {
                    $uibModalInstance.dismiss(dialogResult.no);
                }
                $scope.yes = function () {
                    $uibModalInstance.close(dialogResult.yes);
                }
                $scope.cancel = function () {
                    $uibModalInstance.dismiss(dialogResult.canceled);
                }
            }])
        .controller('inputMessageCtrl', ['$log', '$scope', '$uibModalInstance', 'data', '$translate',
            function ($log, $scope, $uibModalInstance, data, $translate) {
                $scope.header = data.header;
                $scope.msg = data.msg;
                $scope.labelYes = data.labelYes;
                $scope.labelCancel = data.labelCancel;
                $scope.inputMessage = '';
                $scope.save = function () {
                    $uibModalInstance.close($scope.inputMessage)
                };
                $scope.cancel = function () {
                    $uibModalInstance.dismiss(dialogResult.canceled)
                };
                $scope.hitEnter = function (evt) {
                    if (angular.equals(evt.keyCode, 13) && !(angular.equals($scope.inputMessage, null) || angular.equals($scope.inputMessage, '')))
                        $scope.save();
                }
            }])
        // Add default templates via $templateCache
        .run(['$templateCache','$interpolate',function($templateCache,$interpolate){
            // get interpolation symbol (possible that someone may have changed it in their application instead of using '{{}}')
            var startSym = $interpolate.startSymbol();
            var endSym = $interpolate.endSymbol();

            $templateCache.put('/dialogs/custom_confirm.html','<div class="modal-header dialog-header-confirm"><button type="button" class="close" ng-click="no()">&times;</button><h4 class="modal-title"><span class="'+startSym+'icon'+endSym+'"></span> '+startSym+'header'+endSym+'</h4></div><div class="modal-body" ng-bind-html="msg"></div><div class="modal-footer"><button type="button" class="btn btn-primary" ng-click="yes()">'+startSym+'labelYes'+endSym+'</button><button type="button" class="btn btn-default" ng-click="no()">'+startSym+'labelNo'+endSym+'</button></div>');
        }]);
}());