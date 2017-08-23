import {Parse} from "./decorator/parse.decorator";

WorkgroupProvider.$inject = ["RESTEntity","SD"];
function WorkgroupProvider(RESTEntity,SD) {
    /**
     * Приоритет
     * @class
     * @name SD.EntityPriority
     * @extends SD.RESTEntity
     */
    return class Workgroup extends RESTEntity {

        /**
         * Название
         * @property
         * @name SD.Workgroup#name
         * @type {string}
         */
        @Parse( String ) name;

        /**
         *
         * @property
         * @name SD.Workgroup#searchcode
         * @type {string}
         */
        @Parse( String ) searchcode;

        /**
         * Статус
         * @property
         * @name SD.Workgroup#status
         * @type {string}
         */
        @Parse( (data) => SD.EntityStatus.parse(data) ) status;

        toString(){
            return this.name
        }
    };
}

export {WorkgroupProvider};