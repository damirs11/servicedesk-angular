/**
 * Код завершения
 * @class
 * @name SD.EntityClosureCode
 * @extends SD.RESTEntity
 */
export class EntityClosureCode {

    /**
     * Название
     * @property
     * @name SD.EntityClosureCode#name
     * @type {string}
     */
    name: string;

    entityType;

    toString() {
        return this.name;
    }
}
