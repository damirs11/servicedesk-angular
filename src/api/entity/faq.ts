/**
 * База известных ошибок
 * @class
 * @name SD.FAQ
 * @extends SD.RESTEntity
 */
export class FAQ {

    /**
     * Название
     * @property
     * @name SD.FAQ#name
     * @type {string}
     */
    name: string;

    entityType: any;

    toString() {
        return this.name;
    }
}
