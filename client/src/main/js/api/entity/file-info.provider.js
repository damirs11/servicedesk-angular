import {Parse} from "./decorator/parse.decorator";
import {Instantiate, Nullable} from "./decorator/parse-utils";
import {Serialize} from "./decorator/serialize.decorator";
import {serializeId} from "./decorator/serialize-utils";

FileInfoProvider.$inject = ["RESTEntity", "SD", "Upload","$connector"];
function FileInfoProvider(RESTEntity, SD, Upload, $connector) {
    /**
     * Информация о файле. Не имеет своего ID, поэтому кэшируется по fileId
     * @class
     * @extends SD.RESTEntity
     * @name SD.FileInfo
     */
    return class FileInfo extends RESTEntity {
        /**
         * Переопределяем трекер для FileInfo
         * Т.к. fileInfo не имеет ID, она трекерится по
         */
        static $tracker = {
            ownField: "fileId",
            dataField: "fileId",
        };

        /**
         * Название файла
         * @property
         * @name SD.FileInfo#name
         * @type {String}
         */
        @Serialize(String) @Parse( String ) name;
        /**
         * Путь на сервере
         * @property
         * @name SD.FileInfo#path
         * @type {String}
         */
        @Serialize( ) @Parse( Nullable(String) ) path;
        /**
         * ID сущности, к которой привязан файл
         * @property
         * @name SD.FileInfo#entityId
         * @type {Number}
         */
        @Serialize( Nullable(Number) ) entityId;
        /**
         * Тип сущности, к которой привязан файл
         * @property
         * @name SD.FileInfo#entityType
         * @type {SD.EntityType}
         */
        @Serialize( serializeId ) entityType;

        /**
         * Прикрепляет файл к сущности.
         * @method
         * @param entity - сущность, к которой прикрепится вложение
         * @returns {Promise.<SD.Attachment>}
         */
        async attachTo(entity) {
            this.entityId = entity.id;
            this.entityType = SD.EntityType.getFor(entity);
            const jsonData = this.$serialize();
            const data = await $connector.post(`rest/entity/${this.constructor.$entityType}`,null,jsonData);
            return SD.Attachment.parse(data);
        }

        /**
         * Загружает файл на сервер.
         * @static
         * @method
         * @name SD.FileInfo#upload
         * @param file {File} - HTML File объект
         * @returns {Promise.<SD.FileInfo>}
         */
        static async upload(file) {
            const uploading = Upload.upload({
                method: "POST",
                url: "rest/service/file/upload",
                data: {"uploaded-file": file}
            });

            return uploading.then(resp => this.parse(resp.data));
        }

    };
}

export {FileInfoProvider};