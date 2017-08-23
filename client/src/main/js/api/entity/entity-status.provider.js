import {Parse} from "./decorator/parse.decorator";

StatusProvider.$inject = ["RESTEntity"];
function StatusProvider(RESTEntity) {
    /**
     * Статус
     * @class
     * @name SD.EntityStatus
     * @extends SD.RESTEntity
     */
    return class EntityStatus extends RESTEntity {

        /**
         * Название
         * @property
         * @name SD.EntityStatus#name
         * @type {string}
         */
        @Parse( String ) name;

        @Parse( String ) entityType;

        toString(){
            return this.name
        }
    };
}

export {StatusProvider};