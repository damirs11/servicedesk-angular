class ChatInputController {

    onSendButton() {
        this.send({$text: this.input});
    }

    get sendActive() {
        if (this.input == "" || this.input == null) return false;
        return true
    }

}

export {ChatInputController as controller}