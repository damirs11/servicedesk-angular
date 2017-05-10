/**
 * Главное меню
 */
(function () {
    'use strict';

    angular.module('appHeader', [
            'ui.dialogs',
            'appUserData',
            'appPasswordChange'
        ])
        .directive('appHeader', function () {
            return {
                templateUrl: 'js/app/header.html',
                controller: 'appHeaderController'
            }
        })
        .controller('appHeaderController', [
            '$scope', 'USER_DATA', 'appSessionService', 'uiDialogs',
            function ($scope, USER_DATA, appSessionService, uiDialogs) {
                $scope.user = USER_DATA;
                $scope.logout = function() {
                    appSessionService.logout();
                }
                $scope.passwordChange = function() {
                    uiDialogs.create('js/app/passwordChange/passwordChange.html', 'appPasswordChangeController');
                }
                /*$scope.hasRole = function (role) {
                 return $.inArray(role, USER_DATA.authorities) !== -1;
                 }*/
            }]);
}());