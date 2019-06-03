import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {serializeId} from "./decorator/serialize-utils";
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

        /**
         * Отвественный группы
         * @property
         * @name SD.ServiceCall#person
         * @type {SD.Person}
         */
        @Serialize(serializeId) @Parse(data => SD.Person.parse(data)) groupManager;

        toString(){
            return this.name
        }
    };
}

export {WorkgroupProvider};