class ChatLineController {

    DATE_FORMAT = "DD.MM.YYYY (hh:mm)";

    getSideClasses($line){
        if (this.isLeft()) return {"left-message":true};
        if (this.isRight()) return {"right-message":true};
        return {"middle-message":true};
    }

    getTitle(){
        return this.type["title"];
    }

    getRole(){
        return this.type["role"];
    }

    isLeft(){
        return this.type.isLeft
    }

    isRight(){
        return this.type.isRight
    }
}

export {ChatLineController as controller}