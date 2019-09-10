import {Parse} from "./decorator/parse.decorator";
import {Nullable} from "./decorator/parse-utils";

ServiceLevelProvider.$inject = ["SD", "RESTEntity"];
function ServiceLevelProvider(SD, RESTEntity) {
    /**
     * Условия предоставления
     * @class
     * @name SD.ServiceLevel
     */
    class ServiceLevel extends RESTEntity {

        /**
         * Название
         * @property
         * @name SD.ServiceLevel#name
         * @type {string}
         */
        @Parse(String) name;

        /**
         * Название
         * @property
         * @name SD.ServiceLevel#description
         * @type {string}
         */
        @Parse(String) description;

        /**
         * Значение по умолчанию (может быть только один среди всех условий)
         * @property
         * @name SD.ServiceLevel#defaultValue
         * @type {boolean}
         */
        @Parse( Nullable(Boolean) ) defaultValue;

        /**
         * Признак заблокированной записи
         * @property
         * @name SD.ServiceLevel#blocked
         * @type {boolean}
         */
        @Parse( Nullable(Boolean) ) blocked;

        /**
         * Условия предоставления
         * @property
         * @name SD.ServiceLevel#impactSettingList
         * @type {ImpactSettings}
         */
        @Parse((data) => !data ? null : data.map(setting => SD.ImpactSettings.parse(setting))) impactSettingList;

        toString(){
            return String(this.name);
        }

    }
    return ServiceLevel;
}

export {ServiceLevelProvider};