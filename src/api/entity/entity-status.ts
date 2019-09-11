/**
 * Статус
 * @class
 * @name SD.EntityStatus
 * @extends SD.RESTEntity
 */
export class EntityStatus {

    /**
     * Название
     * @property
     * @name SD.EntityStatus#name
     * @type {string}
     */
    name: string;

    order: string;

    toString() {
        return this.name;
    }
}
