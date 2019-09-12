/**
 * Классификация
 * @class
 * @name EntityClassification
 * @extends RESTEntity
 */
export class EntityClassification {

    /**
     * Название
     * @property
     * @name EntityClassification#name
     * @type {string}
     */
    name: string;

    entityType: any;

    toString() {
        return this.name;
    }
}
