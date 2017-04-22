/**
 * Модуль реализует окно для входа пользователя в систему, если он неавторизован
 */
(function () {
    'use strict',
    'ui.router';

    angular.module('appLogin', [
            'appUserData'
        ])
        .config(function ($stateProvider) {
            $stateProvider
                .state('login', {
                    url: '/login',
                    templateUrl: 'js/app/login/login.html',
                    controller: 'appLoginController'
                })
        })
        .controller('appLoginController', [
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
            }]);
}());