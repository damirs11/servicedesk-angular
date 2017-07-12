import {Parse} from "./decorator/parse.decorator";

PriorityProvider.$inject = ["Entity"];
function PriorityProvider(Entity) {
    /**
     * Приоритет
     * @class
     * @name SD.Priority
     */
    return class Priority extends Entity {

        /**
         * Название
         * @property
         * @name SD.Priority#name
         * @type {string}
         */
        @Parse(String) name;

        toString(){
            return this.name
        }
    };
}

export {PriorityProvider};