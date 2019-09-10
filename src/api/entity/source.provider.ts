import {Parse} from "./decorator/parse.decorator";

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
    @Parse( String ) name: string;

    @Parse( String ) entityType: string;

    toString(){
        return this.name;
    }
};