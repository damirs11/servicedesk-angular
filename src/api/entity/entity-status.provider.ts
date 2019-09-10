import {Parse} from "./decorator/parse.decorator";

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
    @Parse( String ) name: string;

    @Parse( String ) entityType: string;

    toString(){
        return this.name;
    }
};
