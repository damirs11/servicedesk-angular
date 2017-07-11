class LoginController {
    static $inject = ['$translate', '$log', 'SD', '$http', '$state', '$scope'];
    constructor($translate, $log, SD, $http, $state, $scope){
        this.$translate = $translate;
        this.SD = SD;
        this.$log = $log;
        this.$http = $http;
        this.$state = $state;
        this.$scope = $scope;
        this.loginFailed = false; // флаг неудачной аутентификации
    }

    // ToDo реализовать returnUrl для app.login. Должно будет после авторизации перебросить на returnUrl

    /**
     * Аутентификация пользователя
     */
    async loginClick(){
        this.loginFailed = false;
        if ((this.$scope.loginForm.$dirty && this.$scope.loginForm.$invalid) || this.$scope.loginForm.$pristine) {
            return;
        }
        try {
            await this.SD.login(this.login,this.password); // Ждем $http.post
        } catch (ignored) { // Неправильные логин или пароль, либо другая ошибка аутентификации
            this.loginFailed = true;
            return;
        }
        this.loginFailed = false;
        this.$state.go("app.main");
    }

    hitEnter (evt) {
        if (angular.equals(evt.keyCode, 13)) this.loginClick();
    }
}

export {LoginController};