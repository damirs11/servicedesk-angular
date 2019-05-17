import {Parse} from "./decorator/parse.decorator";

ServiceLevelPriorityDurationProvider.$inject = ["SD", "RESTEntity"];
function ServiceLevelPriorityDurationProvider(SD, RESTEntity) {
    /**
     * Условие приоритета
     * @class
     * @name SD.ServiceLevelPriority
     */
    class ServiceLevelPriorityDuration extends RESTEntity {
        /**
         * Условия приоритета
         * @property
         * @name SD.ServiceLevelPriorityDuration#serviceLevelPriority
         * @type {SD.ServiceLevelPriority}
         */
        @Parse(data => SD.ServiceLevelPriority.parse(data)) serviceLevelPriority;

        /**
         * Максимальный срок исполнения заявки
         * @property
         * @name SD.ServiceLevelPriorityDuration#maximumDuration
         * @type {number}
         */
        @Parse(Number) maximumDuration;

    }
    return ServiceLevelPriorityDuration;
}
export {ServiceLevelPriorityDurationProvider};