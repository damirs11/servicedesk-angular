import {Parse} from "./decorator/parse.decorator";

EntityCode6Provider.$inject = ["RESTEntity"];
function EntityCode6Provider(RESTEntity) {
    /**
     * Кастомный код 6
     * @class
     * @name SD.EntityCode6
     * @extends SD.RESTEntity
     */
    return class EntityCode6 extends RESTEntity {

        /**
         * Название
         * @property
         * @name SD.EntityCode6#name
         * @type {string}
         */
        @Parse( String ) name;

        @Parse( String ) entityType;

        toString(){
            return this.name;
        }
    };
}

export {EntityCode6Provider};