class ChatInputController {

    onSendButton(type) {
        this.type = type;
        this.send({$text: this.input, $type: this.type});
        this.input = "";
    }

    sendActive(type) {
        if ((this.input == "" || this.input == null) || !this.accessRules.canUpdate(type.fieldName)) return false;
        return true
    }

}

export {ChatInputController as controller}