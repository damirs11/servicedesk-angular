/**
 * Код завершения
 * @class
 * @name EntityClosureCode
 * @extends RESTEntity
 */
export class EntityClosureCode {

    /**
     * Название
     * @property
     * @name EntityClosureCode#name
     * @type {string}
     */
    name: string;

    entityType;

    toString() {
        return this.name;
    }
}
