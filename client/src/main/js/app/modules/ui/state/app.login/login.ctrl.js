class LoginController {
    static $inject = ['$translate', '$log', 'SD', '$http', '$state'];

    constructor($translate, $log, SD, $http, $state){
        this.$translate = $translate;
        this.SD = SD;
        this.$log = $log;
        this.$http = $http;
        this.$state = $state;

        this.loginFailed = false; // флаг неудачной аутентификации
    }

    /**
     * Аутентификация пользователя
     */
    async loginClick(){
        this.loginFailed = false;
        if ((this.loginForm.$dirty && this.loginForm.$invalid) || this.loginForm.$pristine) {
            return;
        }
        try {
            await this.SD.login(this.login,this.password); // Ждем $http.post
        } catch (ignored) { // Если промис post реджектнулся
            this.loginFailed = true;
            return;
        }
        this.loginFailed = false;
        this.$state.go("/");
    }

    hitEnter (evt) {
        if (angular.equals(evt.keyCode, 13)) this.loginClick();
    }
}

export {LoginController}