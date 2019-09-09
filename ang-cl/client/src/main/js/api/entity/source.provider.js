import {Parse} from "./decorator/parse.decorator";

SourceProvider.$inject = ["RESTEntity"];
function SourceProvider(RESTEntity) {
    /**
     * Источник
     * @class
     * @name SD.Source
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

export {SourceProvider};