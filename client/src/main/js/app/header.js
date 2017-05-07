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
            '$rootScope', '$scope', '$translate', '$log', 'USER_DATA', '$http', '$state',
            function ($rootScope, $scope, $translate, $log, USER_DATA, $http, $state) {
                $scope.user = USER_DATA;
                $scope.isAutorized = false;
                $scope.$watch('user.name',
                    function() {
                        $scope.isAutorized = !(USER_DATA.name === $translate.instant('default.login'));
                    }
                );
                $scope.logout = function() {
                    $http.get('rest/service/security/logout').then(
                        function (response) {
                            $http.get('rest/service/config/getInfo').then(
                                function (response) {
                                    angular.copy(response.data.user, USER_DATA);
                                    $state.go("login")
                                }
                            )
                        }
                    );
                }
                /*$scope.hasRole = function (role) {
                 return $.inArray(role, USER_DATA.authorities) !== -1;
                 }*/
            }]);
}());