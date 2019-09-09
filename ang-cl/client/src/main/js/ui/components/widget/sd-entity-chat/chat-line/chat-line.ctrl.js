class ChatLineController {

    DATE_FORMAT = "DD.MM.YYYY (HH:mm)";

    line;
    getSideClasses($line){
        if (this.isLeft()) return {"left-message":true};
        if (this.isRight()) return {"right-message":true};
        return {"middle-message":true};
    }

    getTitle(){
        return this.type["title"];
    }

    isLeft(){
        return !this.line.isOwner
    }

    isRight(){
        return this.line.isOwner
    }
}

export {ChatLineController as controller}