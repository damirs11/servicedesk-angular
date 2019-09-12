/**
 * Источник
 * @class
 * @name Source
 * @extends RESTEntity
 */
export class Source {

    /**
     * Название
     * @property
     * @name Source#name
     * @type {string}
     */
    name: string;

    entityType: string;

    toString() {
        return this.name;
    }
}
