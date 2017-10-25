import mocks from "./attachments.mock.json"

class SDAttachmentsController {

    static $inject = ["$attrs"];

    constructor($attrs){
        this.$attrs = $attrs;
    }

    $onInit(){
        this.SD = this.sd; // SD переданный аттрибутом
        this.attachments = mocks.map($ => this.SD.Attachment.parse($))
    }
}

export {SDAttachmentsController as controller}