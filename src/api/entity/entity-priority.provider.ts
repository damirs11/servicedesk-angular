/**
 * Приоритет
 * @class
 * @name SD.EntityPriority
 * @extends SD.RESTEntity
 */
export class EntityPriority {

    /**
     * Название
     * @property
     * @name SD.EntityPriority#name
     * @type {string}
     */
    name: string;

    order: any;

    toString() {
        return this.name
    }
}
