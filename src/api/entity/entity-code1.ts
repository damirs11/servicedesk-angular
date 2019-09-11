/**
 * Кастомный код 1
 * @class
 * @name SD.EntityCode1
 * @extends SD.RESTEntity
 */
export class EntityCode1 {

    /**
     * Название
     * @property
     * @name SD.EntityCode1#name
     * @type {string}
     */
    name: string;

    entityType: any;

    toString() {
        return this.name;
    }
}
