import {Parse} from "./decorator/parse.decorator";

ServiceLevelPriorityProvider.$inject = ["SD", "RESTEntity"];
function ServiceLevelPriorityProvider(SD, RESTEntity) {
    /**
     * Условие приоритета
     * @class
     * @name SD.ServiceLevelPriority
     */
    class ServiceLevelPriority extends RESTEntity {
        /**
         * Название
         * @property
         * @name SD.ServiceLevelPriority#name
         * @type {string}
         */
        @Parse(String) name;

        /**
         * Порядок следования приоритета
         * @property
         * @name SD.ServiceLevelPriority#order
         * @type {number}
         */
        @Parse(Number) order;

        toString(){
            return String(this.name);
        }

    }
    return ServiceLevelPriority;
}
export {ServiceLevelPriorityProvider};