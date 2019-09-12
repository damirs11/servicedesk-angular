/**
 * Кастомный код 6
 * @class
 * @name EntityCode6
 * @extends RESTEntity
 */
export class EntityCode6 {

    /**
     * Название
     * @property
     * @name EntityCode6#name
     * @type {string}
     */
    name: string;

    entityType: any;

    toString() {
        return this.name;
    }
}
