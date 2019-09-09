import {Parse} from "./decorator/parse.decorator";

EntityClassificationProvider.$inject = ["RESTEntity"];
function EntityClassificationProvider(RESTEntity) {
    /**
     * Классификация
     * @class
     * @name SD.EntityClassification
     * @extends SD.RESTEntity
     */
    return class EntityClassification extends RESTEntity {

        /**
         * Название
         * @property
         * @name SD.EntityClassification#name
         * @type {string}
         */
        @Parse( String ) name;

        @Parse( String ) entityType;

        toString(){
            return this.name
        }
    };
}

export {EntityClassificationProvider};