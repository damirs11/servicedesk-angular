import {Parse} from './decorator/parse.decorator';
import {Instantiate, Nullable} from './decorator/parse-utils';
import {Serialize} from './decorator/serialize.decorator';
import {serializeId} from './decorator/serialize-utils';

/**
 * @class
 * @extends SD.RESTEntity
 * @name SD.FileInfo
 * Информация о файле. Не имеет своего ID, поэтому кэшируется по fileId
 * Используется в основном для загрузки файла на сервер. В других случаях используется Attachment
 * @see SD.Attachment
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
     * @name SD.FileInfo#name
     * @type {String}
     */
    @Serialize(String) name: string;
    /**
     * Путь на сервере
     * @property
     * @name SD.FileInfo#path
     * @type {String}
     */
    @Serialize( ) path: string;

    /**
     * Загружает файл на сервер.
     * @static
     * @method
     * @name SD.FileInfo#upload
     * @param file {File} - HTML File объект
     * @returns {Promise.<SD.FileInfo>}
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
