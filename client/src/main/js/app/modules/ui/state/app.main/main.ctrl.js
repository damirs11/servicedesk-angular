class MainController {
    static $inject = ['SD',"$state"];

    constructor(SD,$state){
        this.SD = SD;
    }
}

export {MainController}