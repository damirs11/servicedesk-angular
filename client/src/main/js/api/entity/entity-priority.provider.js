import {Parse} from "./decorator/parse.decorator";

PriorityProvider.$inject = ["Entity"];
function PriorityProvider(Entity) {
    /**
     * Приоритет
     * @class
     * @name SD.EntityPriority
     * @extends SD.Entity
     */
    return class EntityPriority extends Entity {

        /**
         * Название
         * @property
         * @name SD.EntityPriority#name
         * @type {string}
         */
        @Parse( String ) name;

        toString(){
            return this.name
        }
    };
}

export {PriorityProvider};