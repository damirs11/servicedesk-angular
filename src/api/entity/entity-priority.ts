/**
 * Приоритет
 * @class
 * @name EntityPriority
 * @extends RESTEntity
 */
export class EntityPriority {

    /**
     * Название
     * @property
     * @name EntityPriority#name
     * @type {string}
     */
    name: string;

    order: any;

    toString() {
        return this.name
    }
}
