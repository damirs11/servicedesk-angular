class ChatInputController {

    send(){
        alert("Отправлено.")
    }

    get sendActive() {
        if (this.input == "" || this.input == null) return false;
        return true
    }

}

export {ChatInputController as controller}