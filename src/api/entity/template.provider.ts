import {Parse} from "./decorator/parse.decorator";

/**
 * Приоритет
 * @class
 * @name SD.EntityPriority
 * @extends SD.RESTEntity
 */
export class Template {

    /**
     * Название
     * @property
     * @name SD.Template#name
     * @type {string}
     */
    @Parse( String ) name: string;

    /**
     * Папка
     * @property
     * @name SD.Template#folder
     * @type {Folder}
     */
    @Parse( (data) => SD.Folder.parse(data) ) folder;

    toString(){
        return this.name
    }
};