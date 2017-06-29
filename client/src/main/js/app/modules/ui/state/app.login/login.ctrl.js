class LoginController {
    static $inject = ['$translate', '$log', 'SD', '$http', '$state', '$scope'];

    constructor($translate, $log, SD, $http, $state, $scope){
        this.$translate = $translate;
        this.SD = SD;
        this.$log = $log;
        this.$http = $http;
        this.$state = $state;
        this.$scope = $scope;

        if (SD.authorized) $state.go("app.main");
        this.loginFailed = false; // флаг неудачной аутентификации
    }

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
        } catch (ignored) { // Если промис post реджектнулся
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

export {LoginController}