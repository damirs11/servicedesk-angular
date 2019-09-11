import {Serialize} from './decorator/serialize.decorator';
import {serializeId} from './decorator/serialize-utils';

/**
 * Приоритет
 * @class
 * @name SD.EntityPriority
 * @extends SD.RESTEntity
 */
export class Workgroup {

    /**
     * Название
     * @property
     * @name SD.Workgroup#name
     * @type {string}
     */
    name: string;

    /**
     *
     * @property
     * @name SD.Workgroup#searchcode
     * @type {string}
     */
    searchcode: string;

    /**
     * Статус
     * @property
     * @name SD.Workgroup#status
     * @type {string}
     */
    status: string;

    /**
     * Отвественный группы
     * @property
     * @name SD.ServiceCall#person
     * @type {SD.Person}
     */
    @Serialize(serializeId) groupManager;

    toString() {
        return this.name;
    }
}
