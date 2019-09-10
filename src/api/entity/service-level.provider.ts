import {Parse} from "./decorator/parse.decorator";
import {Nullable} from "./decorator/parse-utils";

/**
 * Условия предоставления
 * @class
 * @name SD.ServiceLevel
 */
export class ServiceLevel {

    /**
     * Название
     * @property
     * @name SD.ServiceLevel#name
     * @type {string}
     */
    @Parse(String) name: string;

    /**
     * Название
     * @property
     * @name SD.ServiceLevel#description
     * @type {string}
     */
    @Parse(String) description: string;

    /**
     * Значение по умолчанию (может быть только один среди всех условий)
     * @property
     * @name SD.ServiceLevel#defaultValue
     * @type {boolean}
     */
    @Parse( Nullable(Boolean) ) defaultValue: boolean;

    /**
     * Признак заблокированной записи
     * @property
     * @name SD.ServiceLevel#blocked
     * @type {boolean}
     */
    @Parse( Nullable(Boolean) ) blocked: boolean;

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