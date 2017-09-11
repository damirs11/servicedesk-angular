import {Parse} from "./decorator/parse.decorator";

EntityClosureCodeProvider.$inject = ["RESTEntity"];
function EntityClosureCodeProvider(RESTEntity) {
    /**
     * Код завершения
     * @class
     * @name SD.EntityClosureCode
     * @extends SD.RESTEntity
     */
    return class EntityClosureCode extends RESTEntity {

        /**
         * Название
         * @property
         * @name SD.EntityClosureCode#name
         * @type {string}
         */
        @Parse( String ) name;

        @Parse( String ) entityType;

        toString(){
            return this.name
        }
    };
}

export {EntityClosureCodeProvider};