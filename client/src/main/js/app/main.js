(function() {
    'use strict';

    var translateDictionary = {};

    angular
        .module('app', [
            'pascalprecht.translate',
            'ui.router',
            'appHeader',
            'appLogin'
        ])
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
        .controller('mainController', [
            'USER_DATA', '$translate', '$state',
            function (USER_DATA, $translate, $state) {
                if (USER_DATA.login === $translate.instant('default.login')) {
                    $state.go("login")
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