class ChatInputController {

    onDefaultButton(text) {
        this.send({$text: text,$type:"doer"});
    }

    get sendActive() {
        if (this.input == "" || this.input == null) return false;
        return true
    }

}

export {ChatInputController as controller}