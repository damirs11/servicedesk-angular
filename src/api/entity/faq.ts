/**
 * База известных ошибок
 * @class
 * @name FAQ
 * @extends RESTEntity
 */
export class FAQ {

    /**
     * Название
     * @property
     * @name FAQ#name
     * @type {string}
     */
    name: string;

    entityType: any;

    toString() {
        return this.name;
    }
}
