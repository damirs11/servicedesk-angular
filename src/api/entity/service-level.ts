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
    name: string;

    /**
     * Название
     * @property
     * @name SD.ServiceLevel#description
     * @type {string}
     */
    description: string;

    /**
     * Значение по умолчанию (может быть только один среди всех условий)
     * @property
     * @name SD.ServiceLevel#defaultValue
     * @type {boolean}
     */
    defaultValue: boolean;

    /**
     * Признак заблокированной записи
     * @property
     * @name SD.ServiceLevel#blocked
     * @type {boolean}
     */
    blocked: boolean;

    /**
     * Условия предоставления
     * @property
     * @name SD.ServiceLevel#impactSettingList
     * @type {ImpactSettings}
     */
    impactSettingList;

    toString() {
        return String(this.name);
    }
}
