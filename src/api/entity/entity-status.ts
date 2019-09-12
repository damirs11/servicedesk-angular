/**
 * Статус
 * @class
 * @name EntityStatus
 * @extends RESTEntity
 */
export class EntityStatus {

    /**
     * Название
     * @property
     * @name EntityStatus#name
     * @type {string}
     */
    name: string;

    order: string;

    toString() {
        return this.name;
    }
}
