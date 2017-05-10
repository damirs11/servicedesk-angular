/**
 * Перехват и обработка ajax-запросов
 */
(function () {
    'use strict';
    angular.module('appErrorHandler', [])
        // register the interceptor as a service
        .factory('_appHttpInterceptor', ['$q', '$log', function($q, $log) {
            return {
                'request': function(config) {
                    // do something on success
                    return config;
                },
                'requestError': function(rejection) {
                    // do something on error
                    return $q.reject(rejection);
                },
                'response': function(response) {
                    // do something on success
                    return response;
                },
                'responseError': function(rejection) {
                    // todo добавить проверку ошибки права доступа
                    if (rejection.status === -1) {
                        alert('Сервер недоступен');
                    }
                    return $q.reject(rejection);
                }
            };
        }])
        .config(['$httpProvider', function ($httpProvider) {
                // Add the interceptor to the $httpProvider.
                $httpProvider.interceptors.push('_appHttpInterceptor')
            }])
}());