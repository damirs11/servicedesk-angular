HttpInterceptorsFactory.$inject = ["$q", "$state", "$injector"];
function HttpInterceptorsFactory($q, $state, $injector) {

    return {
        request (config) {
            // do something on success
            return config;
        },
        requestError (rejection) {
            // do something on error
            return $q.reject(rejection);
        },
        response (response) {
            // do something on success
            return response;
        },
        async responseError (response) {
            const SD = $injector.get("SD"); // ToDo грязный хак. При зависимости от SD - возникает circular dependency. Нужно исправить
            if (response.status === 401) {
                SD.user = null;
                $state.go("app.login", null, {reload: true});
            } else if (response.status === -1) {
                alert('Сервер недоступен');
            }
            return $q.reject(response);
        }
    };
}

HttpInterceptorsConfig.$inject = ["$httpProvider"];
function HttpInterceptorsConfig($httpProvider){
    $httpProvider.interceptors.push('httpInterceptors');
}

export {HttpInterceptorsFactory, HttpInterceptorsConfig};