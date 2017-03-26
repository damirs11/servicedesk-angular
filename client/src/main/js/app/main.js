(function() {
    'use strict'

    var translateDictionary = {}

    angular
        .module('sdApp', [
              'pascalprecht.translate'
        ])
        .config(['$stateProvider', '$urlRouterProvider', '$translateProvider',
            function($stateProvider, $urlRouterProvider, $translateProvider) {
                // Указание страницы по умолчанию
                $urlRouterProvider.otherwise('/')
                // Настройка обработчика главной страницы
                $stateProvider
                    .state('/', {
                        url: '/',
                        templateUrl: 'js/app/main.html'
                    })

                // Настройка источника локализованных сообщений
                $translateProvider.translations('ru', translateDictionary)
                $translateProvider.preferredLanguage('ru')
                $translateProvider.useSanitizeValueStrategy('sanitizeParameters')
            }
        ])

    // Получение информации о текущем пользователе и запуск приложения
    var initInjector = angular.injector(['ng']);
    var $http = initInjector.get('$http')
    $http.get('rest/service/config/getInfo').then(
        function (response) {
            translateDictionary = response.data.translate
            angular.module('userData', []).constant('USER_DATA', response.data.user)
            angular.element(document).ready(function() {
                angular.bootstrap(document, ['sdApp'])
            })
        }
    )
}())