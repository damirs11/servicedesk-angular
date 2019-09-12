/**
 * Классификация
 * @class
 * @name EntityCategory
 * @extends RESTEntity
 */
export class EntityCategory {

    /**
     * Название
     * @property
     * @name EntityCategory#name
     * @type {string}
     */
    name: string;

    entityType: any;

    toString() {
        return this.name;
    }
}
