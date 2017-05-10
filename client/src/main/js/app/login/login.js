/**
 * Модуль реализует окно для входа пользователя в систему, если он неавторизован
 */
(function () {
    'use strict';

    angular.module('appLogin', [
            'appUserData',
            'ngMessages',
            'ui.router'
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
            '$rootScope', '$scope', '$translate', '$log', 'USER_DATA', '$http', '$state',
            function ($rootScope, $scope, $translate, $log, USER_DATA, $http, $state) {
                $scope.loginFailed = false; // флаг неудачной аутентификации
                /**
                 * Аутентификация пользователя
                 */
                $scope.loginClick = function() {
                    $scope.loginFailed = false;
                    if ((this.loginForm.$dirty && this.loginForm.$invalid) || this.loginForm.$pristine) {
                        return;
                    }
                    var params = {login: $scope.login, password: $scope.password};
                    $http.post('rest/service/security/login', params)
                        .then(
                            function() {
                                $scope.loginFailed = false;
                                $http.get('rest/service/config/getInfo').then(
                                    function (response) {
                                        angular.copy(response.data.user, USER_DATA);
                                        $state.go("/")
                                    }
                                )
                            },
                            function() {
                                $scope.loginFailed = true;
                            }
                        );
                };
                $scope.hitEnter = function (evt) {
                    if (angular.equals(evt.keyCode, 13))
                        $scope.loginClick();
                }
            }]);
}());