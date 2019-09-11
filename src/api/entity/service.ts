import {Serialize} from './decorator/serialize.decorator';
import {Nullable} from './decorator/parse-utils';
import {serializeId} from './decorator/serialize-utils';

/**
 * Сервис/услуга
 * @class
 * @name SD.Service
 */
export class Service {

    /**
     * Название
     * @property
     * @name SD.Service#name
     * @type {string}
     */
    name: string;

    /**
     * Статус
     * @property
     * @name SD.Service#status
     * @type {SD.EntityStatus}
     */
    @Serialize(serializeId)
    status;

    /**
     * Папка
     * @property
     * @name SD.Service#folder
     * @type {SD.Folder}
     */
    @Serialize(Nullable(serializeId)) folder;

    toString() {
        return String(this.name);
    }
}