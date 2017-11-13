FileUploaderFactory.$inject = ["$injector"];
function FileUploaderFactory($injector) {
    const $fileUploader = $injector.instantiate(FileUploader,{});
    if ('GULP_REPLACE:DEBUG') window._$fileUploader = $fileUploader;

    return $fileUploader
}


class FileUploader {

    static $inject = ["Upload"];

    constructor(Upload){
        this.Upload = Upload;
    }

    /**
     * Загружает файл на сервер
     * @param file - отправляемый файл
     * @returns {? : Promise}
     */
    upload(file) {
        const uploading = this.Upload.upload({
            method: "POST",
            url: "rest/service/file/upload",
            data: {"uploaded-file": file}
        });

        return uploading.then(resp => resp.data);
    }

    // uploadList(files,onProgress) {
    //     const uploading = this.Upload.upload({
    //         method: "POST",
    //         url: "rest/service/file/uploadList",
    //         data: {files}
    //     });
    //
    //     if (onProgress != undefined) {
    //         return uploading.then(null,null,onProgress)
    //     }
    //     return uploading;
    // }
}

export {FileUploaderFactory};