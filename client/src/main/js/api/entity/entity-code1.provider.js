import {Parse} from "./decorator/parse.decorator";

EntityCode1Provider.$inject = ["RESTEntity"];
function EntityCode1Provider(RESTEntity) {
    /**
     * Кастомный код 1
     * @class
     * @name SD.EntityCode1
     * @extends SD.RESTEntity
     */
    return class EntityCode1 extends RESTEntity {

        /**
         * Название
         * @property
         * @name SD.EntityCode1#name
         * @type {string}
         */
        @Parse( String ) name;

        @Parse( String ) entityType;

        toString(){
            return this.name
        }
    };
}

export {EntityCode1Provider};