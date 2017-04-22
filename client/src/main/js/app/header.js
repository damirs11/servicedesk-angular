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
            '$rootScope', '$scope', '$translate', '$log', 'USER_DATA', '$http',
            function ($rootScope, $scope, $translate, $log, USER_DATA, $http) {
                /**
                 * Авторизация в системе
                 */
                $scope.loginClick = function() {
                    var params = {login: $scope.login, password: $scope.password}
                    $http.post('rest/service/security/login', params)
                        .then(
                            function (response) {
                                $log.info('login success')
                            },
                            function (response) {
                                $log.error('login error')
                            }
                        );
                }
                /**
                 * Выйти из системы. Убить сессию
                 */
                $scope.logoutClick = function () {
                    sessionStorage.clear();
                    // перейти на главную страницу и запросить логин-пароль
                };

                $scope.hasRole = function (role) {
                    return $.inArray(role, USER_DATA.authorities) !== -1;
                }
            }]);
}());