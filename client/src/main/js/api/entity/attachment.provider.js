import {Parse} from "./decorator/parse.decorator";
import {Instantiate} from "./decorator/parse-utils";

const FILETYPE_MAP = {
    "zip": "archive", "rar": "archive", "7z": "archive",
    "gz": "archive", "jar": "archive", "gzip": "archive",
    "tar": "archive", "tgz": "archive", "tar-gz": "archive",

    "doc": "word", "dot": "word", "wbk": "word",
    "docx": "word", "docm": "word", "dotm": "word",
    "docb": "word",

    "xls": "excel", "xlt": "excel", "xlm": "excel",
    "xlsx": "excel", "xlsm": "excel", "xltx": "excel",
    "xltm": "excel", "xlsb": "excel", "xla": "excel",
    "xlam": "excel", "xll": "excel", "xlw": "excel",

    "pdf": "pdf",

    "pptx": "powerpoint", "pptm": "powerpoint", "ppt": "powerpoint",
    "xps": "powerpoint", "potx": "powerpoint", "potm": "powerpoint",
    "pps": "powerpoint", "ppa": "powerpoint", "ppsx": "powerpoint",

    "png": "image", "jpg": "image", "jpeg": "image",
    "exif": "image", "gif": "image", "bmp": "image",
    "ppm": "image", "pgm": "image", "pbm": "image",
    "pnm": "image", "heif": "image", "bpg": "image",
    "tif": "image",

    "txt": "text", "md": "text", "1st": "text",
    "me": "text",

    "3gp": "audio", ".aa": "audio", ".aac": "audio",
    ".aax": "audio", ".act": "audio", "au": "audio",
    "mp3": "audio", "mpc": "audio", "m4a": "audio",
    "ogg": "audio", "oga": "audio", "vox": "audio",
    "wav": "audio", "webm": "audio", "midi": "audio",

};

AttachmentProvider.$inject = ["Entity", "SD"];
function AttachmentProvider(Entity, SD) {
    /**
     * Персона
     * @class
     * @extends SD.RESTEntity
     * @name SD.Attachment
     */
    return class Attachment extends Entity {
        static get $entityType() { // Название на сервере.
            return "FileInfo";
        }
        /**
         * Название вложения
         * @property
         * @name SD.Attachment#name
         * @type {String}
         */
        @Parse( String ) name;
        /**
         * Размер вложения в байтах
         * @property
         * @name SD.Attachment#size
         * @type {Number}
         */
        @Parse( Number ) size;
        /**
         * Персона, создавшая вложение
         * @property
         * @name SD.Attachment#author
         * @type {SD.Person}
         */
        @Parse( data => SD.Person.parse(data) ) author;
        /**
         * Дата прикрепления
         * @property
         * @name SD.Attachment#creationDate
         * @type {SD.Person}
         */
        @Parse( Instantiate(Date) ) creationDate;

        /**
         * Расширение вложение
         * @returns {string|undefined}
         */
        get extension(){
            const index = this.name.lastIndexOf(".");
            if (index < 0) return;
            return this.name.substr(index+1).toLowerCase();
        }

        /**
         * Тип вложения
         * @returns {string}
         */
        get fileType(){
            const fileExt = this.extension;
            if (!fileExt || FILETYPE_MAP[fileExt] == null) return "unknown";
            return FILETYPE_MAP[fileExt];
        }

        /**
         * Ссылка для скачивания;
         * @returns {string}
         */
        get url(){
            return `/rest/service/file/download?id=${this.id}`
        }

    };
}

export {AttachmentProvider};