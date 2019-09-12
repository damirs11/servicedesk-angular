/**
 * Приоритет
 * @class
 * @name EntityPriority
 * @extends RESTEntity
 */
export class Template {

     /**
     * Название
     * @property
     * @name Template#name
     * @type {string}
     */
    name: string;

    /**
     * Папка
     * @property
     * @name Template#folder
     * @type {Folder}
     */
    folder;

    toString() {
        return this.name;
    }
}
