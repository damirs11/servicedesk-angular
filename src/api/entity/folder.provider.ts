/**
 * Папка
 * @class
 * @name SD.Folder
 * @extends SD.RESTEntity
 */
export class Folder {

    /**
     * Название
     * @property
     * @name SD.Folder#name
     * @type {string}
     */
    name: string;

    entityType: any;

    toString() {
        return this.name;
    }
}
