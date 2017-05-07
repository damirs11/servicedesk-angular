/**
 * Главное меню
 */
(function () {
    'use strict';

    angular.module('appHeader', [
            'appUserData'
        ])
        .directive('appHeader', function () {
            return {
                templateUrl: 'js/app/header.html',
                controller: 'appHeaderController'
            }
        })
        .controller('appHeaderController', [
            '$scope', 'USER_DATA', 'appSessionService',
            function ($scope, USER_DATA, appSessionService) {
                $scope.user = USER_DATA;
                $scope.logout = function() {
                    appSessionService.logout();
                }
                /*$scope.hasRole = function (role) {
                 return $.inArray(role, USER_DATA.authorities) !== -1;
                 }*/
            }]);
}());