import {Parse} from "./decorator/parse.decorator";

TemplateProvider.$inject = ["RESTEntity","SD"];
function TemplateProvider(RESTEntity, SD) {
    /**
     * Приоритет
     * @class
     * @name SD.EntityPriority
     * @extends SD.RESTEntity
     */
    return class Template extends RESTEntity {

        /**
         * Название
         * @property
         * @name SD.Template#name
         * @type {string}
         */
        @Parse( String ) name;

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
}

export {TemplateProvider};