import {Parse} from "./decorator/parse.decorator";
import {Instantiate, Nullable} from "./decorator/parse-utils";
import {Serialize} from "./decorator/serialize.decorator";

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
         * Дата создания
         * @property
         * @name SD.FileInfo#size
         * @type {Date}
         */
        @Serialize(Number) @Parse( Instantiate(Date) ) creationDate;
        /**
         * Размер файла
         * @property
         * @name SD.FileInfo#size
         * @type {Number}
         */
        @Serialize(Number) @Parse( Number ) size;


        @Serialize( ) @Parse( Nullable(String) ) path;
        @Serialize( ) @Parse( Nullable(Number) ) id;


        /**
         * Прикрепляет файл к сущности.
         * @method
         * @param entity - сущность, к которой прикрепится вложение
         * @returns {Promise.<SD.Attachment>}
         */
        async attachTo(entity) {
            const jsonData = this.$serialize();
            let entityId;
            if (typeof entity == "number") {
                entityId = entity
            } else {
                entityId = entity.id; // Тут мы привязаны к полю ID (не к tracker'у)
            }
            jsonData.entityId = entityId;
            jsonData.entityType = SD.EntityType.getFor(this);
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