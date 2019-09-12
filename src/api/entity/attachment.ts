import { FILETYPE_MAP } from './util/filetype-list';

/**
 * Вложение. Используется для вложений / аватарок и т.п.
 * Класс не имеет своих методов работы с REST, они внедряются в другие сущности
 * @see ENTITY_MIXIN.AttachmentsHolder
 * Для загрузки файлов на сервер используется FileInfo
 * @see FileInfo
 * @class
 * @extends RESTEntity
 * @name Attachment
 */
export class Attachment {
    static get $entityType() { // Название на сервере.
        return 'FileInfo';
    }
    /**
     * Название вложения
     * @property
     * @name Attachment#name
     * @type {String}
     */
    name: string;
    /**
     * Размер вложения в байтах
     * @property
     * @name Attachment#size
     * @type {Number}
     */
    size: number;
    /**
     * Персона, создавшая вложение
     * @property
     * @name Attachment#author
     * @type {Person}
     */
    author;
    /**
     * Дата прикрепления
     * @property
     * @name Attachment#creationDate
     * @type {Person}
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
