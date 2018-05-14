class EntityChatController {

    busy = false;

    DATE_FORMAT = "DD.MM.YYYY (HH:mm)";

    constructor(){
    }

    $onInit(){
        this.fetchChat();
    }

    async fetchChat(){
        this.chatLines = await this.entity.getChat()
    }

    async send(text, type){
        this.busy = true;
        await this.entity.sendChatMessage(text, type);
        await this.fetchChat();
        this.busy = false;
    }

    getType($line){
        const typeName = $line.type;
        return this.typeMap[typeName];
    }
}

export {EntityChatController as controller}
