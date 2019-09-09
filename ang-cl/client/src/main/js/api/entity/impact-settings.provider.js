import {Parse} from "./decorator/parse.decorator";

ImpactSettingsProvider.$inject = ["SD", "RESTEntity"];
function ImpactSettingsProvider(SD, RESTEntity) {
    /**
     * Условия предоставления. Определяет крайние сроки в заявках в зависимости от приоритета
     * @class
     * @name SD.ImpactSettings
     */
    class ImpactSettings extends RESTEntity {

        /**
         * Приоритет
         * @property
         * @name SD.ImpactSettings#priority
         * @type {SD.EntityPriority}
         */
        @Parse(data => SD.EntityPriority.parse(data)) priority;

        /**
         * Условия приоритета
         * @property
         * @name SD.ImpactSettings#serviceLevelPriority
         * @type {SD.ServiceLevelPriority}
         */
        @Parse(data => SD.ServiceLevelPriority.parse(data)) serviceLevelPriority;

    }
    return ImpactSettings;
}

export {ImpactSettingsProvider};