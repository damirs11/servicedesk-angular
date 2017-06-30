HttpInterceptorsConfig.$inject = ["$httpProvider"];

export function HttpInterceptorsConfig($httpProvider){
    $httpProvider.interceptors.push('errorInterceptors');
}