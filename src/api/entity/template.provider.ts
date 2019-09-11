
/**
 * Приоритет
 * @class
 * @name SD.EntityPriority
 * @extends SD.RESTEntity
 */
export class Template {

     /**
     * Название
     * @property
     * @name SD.Template#name
     * @type {string}
     */
    name: string;

    /**
     * Папка
     * @property
     * @name SD.Template#folder
     * @type {Folder}
     */
    folder;

    toString() {
        return this.name;
    }
}
