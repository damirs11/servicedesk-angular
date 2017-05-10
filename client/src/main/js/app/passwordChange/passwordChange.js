/**
 * Окно для смены пароля у пользователя
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
            '$scope', '$http', 'modalResult', 'uiDialogs', '$uibModalInstance',
            function ($scope, $http, modalResult, uiDialogs, $uibModalInstance) {
                $scope.loginFailed = false;
                $scope.minLength = 6; // минимальная длина пароля
                $scope.maxLength = 50; // максимальная длина пароля
                $scope.oldPassword = null; // прежний пароль
                $scope.newPassword = null; // новый пароль
                $scope.confirmPassword = null; // повторно введенный новый пароль
                /**
                 * Запрос к серверу на смену пароля
                 */
                $scope.save = function() {
                    if (this.passwordChangeForm.$invalid) {
                        return;
                    }
                    $scope.loginFailed = false;
                    var params = {
                        oldPassword: $scope.oldPassword,
                        newPassword: $scope.newPassword};
                    $http.post('rest/service/security/passwordChange', params)
                        .then(
                            function() {
                                $uibModalInstance.close(modalResult.ok);
                                $scope.$destroy();
                            },
                            function(response) {
                                $scope.loginFailed = true;
                            }
                        );
                };
                $scope.close = function () {
                    $uibModalInstance.dismiss(modalResult.cancel);
                    $scope.$destroy();
                };
            }]);
}());