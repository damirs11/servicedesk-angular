/**
 * Окно смены пароля пользователя
 */
(function () {
    'use strict';

    angular
        .module('appPasswordChange', [
            'pascalprecht.translate',
            'ui.dialogs',
            'ui.bootstrap.modal',
            'appValidators'
        ])
        .controller('appPasswordChangeController', [
            '$scope', '$http', 'modalResult', 'uiDialogs', '$uibModalInstance', '$translate',
            function ($scope, $http, modalResult, uiDialogs, $uibModalInstance, $translate) {
                $scope.minLength = 6; // минимальная длина пароля
                $scope.maxLength = 50; // максимальная длина пароля
                $scope.oldPassword; // прежний пароль
                $scope.newPassword; // новый пароль
                $scope.confirmPassword; // повторно введенный новый пароль
                /**
                 * Запрос к серверу на смену пароля
                 */
                $scope.save = function() {
                    //if ((this.passwordChangeForm.$dirty && this.passwordChangeForm.$invalid) || this.passwordChangeForm.$pristine) {
                    if (this.passwordChangeForm.$invalid) {
                        return;
                    }
                    var params = {
                        oldPassword: $scope.oldPassword,
                        newPassword: $scope.newPassword,
                        confirmPassword: $scope.confirmPassword};
                    $http.post('rest/service/security/passwordChange', params)
                        .then(
                            function() {
                                $uibModalInstance.close(modalResult.ok);
                                $scope.$destroy();
                            },
                            function(response) {
                                uiDialogs.error(null, response.data);
                            }
                        );
                };
                $scope.close = function () {
                    $uibModalInstance.dismiss(modalResult.cancel);
                    $scope.$destroy();
                };
            }]);
}());