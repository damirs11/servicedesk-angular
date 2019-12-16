class LoginController {
    static $inject = ["$translate", "$log", "Session", "$http", "$state", "$scope", "$window", "returnUrl"];
    constructor($translate, $log, Session, $http, $state, $scope, $window, returnUrl){
        this.$translate = $translate;
        this.Session = Session;
        this.$log = $log;
        this.$http = $http;
        this.$state = $state;
        this.$scope = $scope;
        this.$window = $window;
        this.loginFailed = false; // флаг неудачной аутентификации
        this.returnUrl = returnUrl;
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
            await this.Session.login(this.login,this.password); // Ждем $http.post
        } catch (ignored) { // Неправильные логин или пароль, либо другая ошибка аутентификации
            this.loginFailed = true;
            return;
        }
        this.loginFailed = false;
        if (this.returnUrl) {
            this.$window.location.href = this.returnUrl
        } else {
            this.$state.go("app.main");
        }
    }

    hitEnter (evt) {
        if (angular.equals(evt.keyCode, 13)) this.loginClick();
    }
}

export {LoginController};