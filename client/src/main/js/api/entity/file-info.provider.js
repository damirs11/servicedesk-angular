import {Parse} from "./decorator/parse.decorator";
import {Instantiate} from "./decorator/parse-utils";

FileInfoProvider.$inject = ["RESTEntity", "SD"];
function FileInfoProvider(RESTEntity, SD) {
    /**
     * Персона
     * @class
     * @extends SD.RESTEntity
     * @name SD.FileInfo
     */
    return class FileInfo extends RESTEntity {
        static parse(data) {
            if (!data) return null;
            if (typeof data !== "object") return new this(data);
            return new this(data.fileId).$update(data)
        }

        static get $entityType() { // Название на сервере.
            return "FileInfo";
        }
        /**
         * Название файла
         * @property
         * @name SD.FileInfo#name
         * @type {String}
         */
        @Parse( String ) name;
        /**
         * Размер файла в байтах
         * @property
         * @name SD.FileInfo#size
         * @type {Number}
         */
        @Parse( Number ) size;


    };
}

export {FileInfoProvider};