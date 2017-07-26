import {Parse} from "./decorator/parse.decorator";

StatusProvider.$inject = ["Entity"];
function StatusProvider(Entity) {
    /**
     * Статус
     * @class
     * @name SD.Status
     */
    return class Status extends Entity {

        /**
         * Название
         * @property
         * @name SD.Status#name
         * @type {string}
         */
        @Parse(String) name;

        static get name(){
            return "EntityStatus"
        }

        toString(){
            return this.name
        }
    };
}

export {StatusProvider};