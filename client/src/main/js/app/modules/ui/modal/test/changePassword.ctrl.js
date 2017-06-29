export class ChangePasswordController{

    static $inject = ["$modalState"];

    constructor($modalState){
        console.log("Hello, password chaadsasddsaasdnge!")
        this.$modalState = $modalState
    }

    close(){
        console.log("closing");
        this.$modalState.resolve()
    }
}