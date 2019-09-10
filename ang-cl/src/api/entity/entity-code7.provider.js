import {Parse} from "./decorator/parse.decorator";

EntityCode7Provider.$inject = ["RESTEntity"];
function EntityCode7Provider(RESTEntity) {
    /**
     * Кастомный код 7
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
            return this.name;
        }
    };
}

export {EntityCode7Provider};