/**
 * Условие приоритета
 * @class
 * @name ServiceLevelPriority
 */
export class ServiceLevelPriority {
    /**
     * Название
     * @property
     * @name ServiceLevelPriority#name
     * @type {string}
     */
    name: string;

    /**
     * Порядок следования приоритета
     * @property
     * @name ServiceLevelPriority#order
     * @type {number}
     */
    order: number;

    toString() {
        return String(this.name);
    }
}
