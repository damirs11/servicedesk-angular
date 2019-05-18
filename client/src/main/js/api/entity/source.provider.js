import {Parse} from "./decorator/parse.decorator";

Source.$inject = ["RESTEntity"];
function Source(RESTEntity) {
    /**
     * Источник
     * @class
     * @name SD.EntityCode1
     * @extends SD.RESTEntity
     */
    return class Source extends RESTEntity {

        /**
         * Название
         * @property
         * @name SD.Source#name
         * @type {string}
         */
        @Parse( String ) name;

        @Parse( String ) entityType;

        toString(){
            return this.name;
        }
    };
}

export {Source};