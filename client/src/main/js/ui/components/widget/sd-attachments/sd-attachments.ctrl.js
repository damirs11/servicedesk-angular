import mocks from "./attachments.mock.json"

class SDAttachmentsController {

    static $inject = ["$attrs"];
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

    /**
     * Список отображаемых вложений.
     * @type {SD.Attachment[]}
     */
    attachments = [];

    constructor($attrs){
        this.$attrs = $attrs;
    }

    async $onInit(){
        this.SD = this.sd; // SD переданный аттрибутом
        this.attachments = await this.target.getAttachments();
    }

    /**
     * @param files {File[]}
     */
    $onFilePicked(files) {
        files.forEach(file => {
                const uploadingInfo = {file: file, error: false};
                const onProgress = (event) => {uploadingInfo.progress = Math.floor(event.loaded/event.total*100)};
                const uploadingProcess = this.SD.FileInfo.upload(file);
                uploadingInfo.abort = () => { // Функция вызывается при нажатии на крестик.
                    if (!uploadingInfo.error) {
                        uploadingProcess.abort();
                    }
                    this.removeUploadingInfo(uploadingInfo)
                };
                uploadingProcess.then(
                    (fileInfo) => this.$onFileUploaded(uploadingInfo,fileInfo),
                    (resp) => this.$onFileUploadError(uploadingInfo,resp),
                    onProgress
                );
                this.uploadingInfos.push(uploadingInfo);
            })
    }

    removeUploadingInfo(uploadingInfo) {
        const index = this.uploadingInfos.indexOf(uploadingInfo);
        this.uploadingInfos.splice(index,1);
    }

    async $onFileUploaded(uploadingInfo,fileInfo) {
        // ToDO attach
        this.removeUploadingInfo(uploadingInfo);
        const attachment = await this.target.attachFile(fileInfo);
        this.attachments.push(attachment)
        // ToDo Прикрепить вложение и обновить список вложений
    }

    async $onFileUploadError(uploadingInfo,resp) {
        uploadingInfo.error = true;
    }
}

export {SDAttachmentsController as controller}