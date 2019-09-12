/**
 * Кастомный код 1
 * @class
 * @name EntityCode1
 * @extends RESTEntity
 */
export class EntityCode1 {

    /**
     * Название
     * @property
     * @name EntityCode1#name
     * @type {string}
     */
    name: string;

    entityType: any;

    toString() {
        return this.name;
    }
}
