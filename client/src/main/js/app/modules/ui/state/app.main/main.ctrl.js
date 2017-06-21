class MainController {
    static $inject = ['SD',"$state"];

    constructor(SD,$state){
        this.SD = SD;
        console.log("Main",SD.user);
        if (!SD.user) $state.go("login")
    }
}

export {MainController}