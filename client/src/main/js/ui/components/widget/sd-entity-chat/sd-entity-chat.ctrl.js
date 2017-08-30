import mockLines from "./mock-lines.json";


class EntityChatController {

    DATE_FORMAT = "DD.MM.YYYY (hh:mm)";

    static $inject = ["SD"];

    constructor(SD){
        this.SD = SD;
    }

    $onInit(){
        this.fetchChat();
    }

    async fetchChat(){
        // this.chatLines = await this.entity.getChat()
        this.chatLines = mockLines.map(::this.SD.HistoryLine.parse)
    }


    getType($line){
        const typeName = $line.type;
        return this.typeMap[typeName];
    }
}

export {EntityChatController as controller}
