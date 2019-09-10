import {Parse} from "./decorator/parse.decorator";

/**
 * Условие приоритета
 * @class
 * @name SD.ServiceLevelPriority
 */
export class ServiceLevelPriority {
    /**
     * Название
     * @property
     * @name SD.ServiceLevelPriority#name
     * @type {string}
     */
    @Parse(String) name: string;

    /**
     * Порядок следования приоритета
     * @property
     * @name SD.ServiceLevelPriority#order
     * @type {number}
     */
    @Parse(Number) order: number;

    toString(){
        return String(this.name);
    }
}