import {Parse} from "./decorator/parse.decorator";
import {Nullable} from "./decorator/parse-utils";

PriorityProvider.$inject = ["RESTEntity"];
function PriorityProvider(RESTEntity) {
    /**
     * Приоритет
     * @class
     * @name SD.EntityPriority
     * @extends SD.RESTEntity
     */
    return class EntityPriority extends RESTEntity {

        /**
         * Название
         * @property
         * @name SD.EntityPriority#name
         * @type {string}
         */
        @Parse( String ) name;

        @Parse( Nullable(Number) ) order;

        toString(){
            return this.name
        }

    };
}

export {PriorityProvider};