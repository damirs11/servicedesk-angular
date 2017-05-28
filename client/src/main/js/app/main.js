(function() {
    'use strict';

    var translateDictionary = {};

    angular
        .module('app', [
            // angular
            'pascalprecht.translate',
            'ngSanitize',
            // angular ui
            'ui.router',
            'ui.bootstrap',
            // app widgets
            'ui.dialogs',
            'ui.test',
            // app
            'appHeader',
            'appLogin',
            'appChanges',
            'appErrorHandler'
        ])
        // todo добавить сервис для обновления getInfo
        .config(['$stateProvider', '$urlRouterProvider', '$translateProvider',
            function($stateProvider, $urlRouterProvider, $translateProvider) {
                // Указание страницы по умолчанию
                $urlRouterProvider.otherwise('/');
                // Настройка обработчика главной страницы
                $stateProvider
                    .state('/', {
                        url: '/',
                        templateUrl: 'js/app/main.html',
                        controller: 'mainController'
                    });

                // Настройка источника локализованных сообщений
                $translateProvider.translations('ru', translateDictionary);
                $translateProvider.preferredLanguage('ru');
                $translateProvider.useSanitizeValueStrategy('sanitizeParameters');
            }
        ])
        .controller('mainController', ['appSessionService', function (appSessionService) {
                appSessionService.checkUser();
            }])
        .factory('appSessionService', [
            'USER_DATA', '$translate', '$state', '$http',
            function (USER_DATA, $translate, $state, $http) {
                /** Если текущий пользователь не авторизован, то перенаправляем его на форму ввода пароля */
                function checkUser() {
                    if (USER_DATA.login === $translate.instant('default.login')) {
                        $state.go("login")
                    }
                }
                /** Запрашивает на сервере актуальную информацию о текущем пользователе */
                function checkSession() {
                    $http.get('rest/service/config/getInfo').then(
                        function (response) {
                            angular.copy(response.data.user, USER_DATA);
                            checkUser();
                        }
                    )
                }
                /** Осуществляет выход пользователя из системы */
                function logout() {
                    $http.get('rest/service/security/logout').then(
                        checkSession
                    );
                }
                return {
                    checkUser: checkUser,
                    checkSession: checkSession,
                    logout: logout
                }
            }]);


    // Получение информации о текущем пользователе и запуск приложения
    var initInjector = angular.injector(['ng']);
    var $http = initInjector.get('$http');
    $http.get('rest/service/config/getInfo').then(
        function (response) {
            translateDictionary = response.data.translate;
            angular.module('appUserData', []).constant('USER_DATA', response.data.user);
            angular.element(document).ready(function() {
                angular.bootstrap(document, ['app']);
            })
        },
        function() {
            alert('Сервер недоступен. Повторите попытку позже :(')
        }
    );
}());