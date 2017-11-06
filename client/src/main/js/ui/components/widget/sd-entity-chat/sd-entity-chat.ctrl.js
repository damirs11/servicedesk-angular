class EntityChatController {

    DATE_FORMAT = "DD.MM.YYYY (hh:mm)";

    constructor(){
    }

    $onInit(){
        this.fetchChat();
    }

    async fetchChat(){
        this.chatLines = await this.entity.getChat()
    }


    getType($line){
        const typeName = $line.type;
        return this.typeMap[typeName];
    }
}

export {EntityChatController as controller}
