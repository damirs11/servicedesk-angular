
/**
 * Источник
 * @class
 * @name SD.Source
 * @extends SD.RESTEntity
 */
export class Source {

    /**
     * Название
     * @property
     * @name SD.Source#name
     * @type {string}
     */
    name: string;

    entityType: string;

    toString() {
        return this.name;
    }
}
