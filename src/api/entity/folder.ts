/**
 * Папка
 * @class
 * @name Folder
 * @extends RESTEntity
 */
export class Folder {

    /**
     * Название
     * @property
     * @name Folder#name
     * @type {string}
     */
    name: string;

    entityType: any;

    toString() {
        return this.name;
    }
}
