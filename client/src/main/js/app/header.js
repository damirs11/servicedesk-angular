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
            '$rootScope', '$scope', '$translate', '$log', 'USER_DATA',
            function ($rootScope, $scope, $translate, $log, USER_DATA) {
                $scope.user = USER_DATA;
                $scope.isAutorized = false;
                $scope.$watch('user.name',
                    function() {
                        $scope.isAutorized = !(USER_DATA.name === $translate.instant('default.login'));
                    }
                );
                $scope.logout = function() {
                    $log.log('logout');
                }
                /*$scope.hasRole = function (role) {
                 return $.inArray(role, USER_DATA.authorities) !== -1;
                 }*/
            }]);
}());