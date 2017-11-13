import mocks from "./attachments.mock.json"

class SDAttachmentsController {

    static $inject = ["$attrs","$fileUploader","$fileUploader"];
    /**
     * [{
     *      file: File               // сам файл
     *      progress: number         // прогресс загрузки 0 - 100
     *      error: boolean           // произошла ошибка при загрузке
     *      abort: func              // фукнция, для отмены загрузки
     * }]
     * @type {Array}
     */
    uploadingInfos = [];

    constructor($attrs,$fileUploader){
        this.$attrs = $attrs;
        this.$fileUploader = $fileUploader;
    }

    $onInit(){
        this.SD = this.sd; // SD переданный аттрибутом
        this.attachments = mocks.map($ => this.SD.Attachment.parse($))
    }

    /**
     * @param files {File[]}
     */
    $onFileAttach(files) {
        files.forEach(file => {
                const uploadingInfo = {file: file, error: false};
                const onProgress = (event) => {uploadingInfo.progress = Math.floor(event.loaded/event.total*100)};
                const uploadingProcess = this.$fileUploader.upload(file);
                uploadingInfo.abort = uploadingProcess.abort;
                uploadingProcess.then(
                    (fileInfo) => this.$onFileUploaded(uploadingInfo,fileInfo),
                    (resp) => this.$onFileUploadError(file,resp),
                    onProgress
                );
                this.uploadingInfos.push(uploadingInfo);
            })
    }

    async $onFileUploaded(uploadingInfo,fileInfo) {
        // ToDO attach
        const index = this.uploadingInfos.indexOf(uploadingInfo);
        this.uploadingInfos.splice(index,1);
        // ToDo Прикрепить вложение и обновить список вложений
    }

    async $onFileUploadError(file,resp) {
        console.log(file,resp,"ERROR")
    }
}

export {SDAttachmentsController as controller}