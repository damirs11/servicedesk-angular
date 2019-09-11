/**
 * Кастомный код 6
 * @class
 * @name SD.EntityCode6
 * @extends SD.RESTEntity
 */
export class EntityCode6 {

    /**
     * Название
     * @property
     * @name SD.EntityCode6#name
     * @type {string}
     */
    name: string;

    entityType: any;

    toString() {
        return this.name;
    }
}
