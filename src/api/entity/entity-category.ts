import {Parse} from './decorator/parse.decorator';

    /**
     * Классификация
     * @class
     * @name SD.EntityCategory
     * @extends SD.RESTEntity
     */
export class EntityCategory {

    /**
     * Название
     * @property
     * @name SD.EntityCategory#name
     * @type {string}
     */
    name: string;

    entityType: any;

    toString() {
        return this.name;
    }
}
