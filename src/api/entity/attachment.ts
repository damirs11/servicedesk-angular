import { FILETYPE_MAP } from './util/filetype-list';

/**
 * Вложение. Используется для вложений / аватарок и т.п.
 * Класс не имеет своих методов работы с REST, они внедряются в другие сущности
 * @see ENTITY_MIXIN.AttachmentsHolder
 * Для загрузки файлов на сервер используется FileInfo
 * @see SD.FileInfo
 * @class
 * @extends SD.RESTEntity
 * @name SD.Attachment
 */
export class Attachment {
    static get $entityType() { // Название на сервере.
        return 'FileInfo';
    }
    /**
     * Название вложения
     * @property
     * @name SD.Attachment#name
     * @type {String}
     */
    name: string;
    /**
     * Размер вложения в байтах
     * @property
     * @name SD.Attachment#size
     * @type {Number}
     */
    size: number;
    /**
     * Персона, создавшая вложение
     * @property
     * @name SD.Attachment#author
     * @type {SD.Person}
     */
    author;
    /**
     * Дата прикрепления
     * @property
     * @name SD.Attachment#creationDate
     * @type {SD.Person}
     */
    creationDate;

    /**
     * Расширение вложение
     * @returns {string|undefined}
     */
    get extension(): string | undefined {
        const index = this.name.lastIndexOf('.');
        if (index < 0) { return; }
        return this.name.substr(index + 1).toLowerCase();
    }

    /**
     * Тип вложения
     * @returns {string}
     */
    get fileType(): string {
        const fileExt = this.extension;
        if (!fileExt || FILETYPE_MAP[fileExt] == null) { return 'unknown'; }
        return FILETYPE_MAP[fileExt];
    }

    /**
     * Ссылка для скачивания;
     * @returns {string}
     */
    // get url(): string {
    //     return `rest/service/file/download?id=${this.id}`;
    // }
}
