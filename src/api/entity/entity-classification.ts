/**
 * Классификация
 * @class
 * @name SD.EntityClassification
 * @extends SD.RESTEntity
 */
export class EntityClassification {

    /**
     * Название
     * @property
     * @name SD.EntityClassification#name
     * @type {string}
     */
    name: string;

    entityType: any;

    toString() {
        return this.name;
    }
}
