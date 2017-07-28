import {Parse} from "./decorator/parse.decorator";

StatusProvider.$inject = ["Entity"];
function StatusProvider(Entity) {
    /**
     * Статус
     * @class
     * @name SD.EntityStatus
     * @extends SD.Entity
     */
    return class EntityStatus extends Entity {

        /**
         * Название
         * @property
         * @name SD.EntityStatus#name
         * @type {string}
         */
        @Parse(String) name;

        toString(){
            return this.name
        }
    };
}

export {StatusProvider};