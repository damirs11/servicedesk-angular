/**
 * @class
 * @extends RESTEntity
 * @name FileInfo
 * Информация о файле. Не имеет своего ID, поэтому кэшируется по fileId
 * Используется в основном для загрузки файла на сервер. В других случаях используется Attachment
 * @see Attachment
 */
export class FileInfo {
    /**
     * Переопределяем трекер для FileInfo
     * Т.к. fileInfo не имеет ID, она трекерится по
     */
    static $tracker = {
        ownField: 'fileId',
        dataField: 'fileId',
    };

    /**
     * Название файла
     * @property
     * @name FileInfo#name
     * @type {String}
     */
     name: string;
    /**
     * Путь на сервере
     * @property
     * @name FileInfo#path
     * @type {String}
     */
     path: string;

    /**
     * Загружает файл на сервер.
     * @static
     * @method
     * @name FileInfo#upload
     * @param file {File} - HTML File объект
     * @returns {Promise.<FileInfo>}
     */
    // static async upload(file) {
    //     const uploading = Upload.upload({
    //         method: "POST",
    //         url: "rest/service/file/upload",
    //         data: {"uploaded-file": file}
    //     });

    //     return uploading.then(resp => this.parse(resp.data));
    // }
}
