class EntityChatController {

    DATE_FORMAT = "DD.MM.YYYY (HH:mm)";

    constructor(){
    }

    $onInit(){
        this.fetchChat();
    }

    async fetchChat(){
        this.chatLines = await this.entity.getChat()
    }

    send(text, type){
        this.entity.sendChatMessage(text, type);
        this.fetchChat();
    }

    getType($line){
        const typeName = $line.type;
        return this.typeMap[typeName];
    }
}

export {EntityChatController as controller}
