import mocks from "./attachments.mock.json"

class SDAttachmentsController {

    static $inject = ["$attrs"];
    /**
     * [{
     *      file: File               // сам файл
     *      progress: number         // прогресс загрузки 0 - 100
     *      status: string           // pending / error / success
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

    // Промис загрузки вложений
    loadingPromise ;

    constructor($attrs){
        this.$attrs = $attrs;
    }

    async $onInit(){
        this.SD = this.sd; // SD переданный аттрибутом
        this.loadingPromise = this.target.getAttachments();
        this.attachments = await this.loadingPromise;
        this.loading = false; // Убираем спиннер-загрузку.L
    }

    /**
     * @param files {File[]}
     */
    $onFilePicked(files) {
        files.forEach(file => {
                const uploadingInfo = {file: file, status: "pending"};
                const onProgress = (event) => {uploadingInfo.progress = Math.floor(event.loaded/event.total*100)};
                const uploadingProcess = this.SD.FileInfo.upload(file);
                uploadingInfo.abort = () => { // Функция вызывается при нажатии на крестик.
                    if (uploadingInfo.status == "pending") {
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
        uploadingInfo.status = "success";
        try {
            const attachment = await this.target.attachFile(fileInfo);
            this.removeUploadingInfo(uploadingInfo);
            this.attachments.push(attachment)
        } catch (e) {
            uploadingInfo.status = "error";
        }
    }

    async $onFileUploadError(uploadingInfo,resp) {
        uploadingInfo.status = "error";
    }
}

export {SDAttachmentsController as controller}