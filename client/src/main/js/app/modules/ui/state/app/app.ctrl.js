class AppController {
    static $inject = ['SD', 'uiDialogs', '$state'];

    constructor(SD, uiDialogs, $state){
        //this.appSessionService = appSessionService;
        this.uiDialogs = uiDialogs;
        this.SD = SD;
    }

    get user(){
        return this.SD.user;
    }

    get authorized(){
        return this.SD.authorized;
    }

    logout() {
        this.SD.logout().catch(e => {
            alert("Не удалось выйти");
        });
    }

    passwordChange() {
        this.uiDialogs.create('js/app/passwordChange/passwordChange.html', 'appPasswordChangeController');
    }
}

export {AppController}