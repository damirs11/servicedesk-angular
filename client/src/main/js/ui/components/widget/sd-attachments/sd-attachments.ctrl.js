import mocks from "./attachments.mock.json"

class SDAttachmentsController {

    static $inject = ["$attrs"];
    uploadingFiles = [];

    constructor($attrs){
        this.$attrs = $attrs;
    }

    $onInit(){
        this.SD = this.sd; // SD переданный аттрибутом
        this.attachments = mocks.map($ => this.SD.Attachment.parse($))
    }

    onFileAttach(files) {
        this.uploadingFiles = this.uploadingFiles.concat(files);
    }
}

export {SDAttachmentsController as controller}