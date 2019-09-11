import {Parse} from './decorator/parse.decorator';
import {Serialize} from './decorator/serialize.decorator';
import {Nullable} from './decorator/parse-utils';
import {serializeId} from './decorator/serialize-utils';
import {Mixin} from './mixin/mixin.decorator';

/**
 * Объект обслуживания
 * @class
 * @name SD.ConfigurationItem
 * @extends SD.EditableEntity
 */
export class ConfigurationItem {
    static $entityTypeId = 796000260;
    /**
     * Номер
     * @property
     * @name SD.ConfigurationItem#no
     * @type {number}
     */
    @Serialize(Number) no: number;

    /**
     * Код поиска
     * @property
     * @name SD.ConfigurationItem#searchCode
     * @type {string}
     */
    @Serialize(String) searchCode: string;

    /**
     * Название
     * @property
     * @name SD.ConfigurationItem#name
     * @type {string}
     */
    @Serialize(String) name: string;

    /**
     * Дополнительные сведения
     * @property
     * @name SD.ConfigurationItem#description
     * @type {string}
     */
    @Serialize(String) description: string;

    /**
     * Счет-фактура
     * @property
     * @name SD.ConfigurationItem#orderNr
     * @type {string}
     */
    @Serialize(String) orderNr: string;

    /**
     * серийный номер
     * @property
     * @name SD.ConfigurationItem#serial
     * @type {string}
     */
    @Serialize(String) serial: string;

    /**
     * Адрес
     * @property
     * @name SD.ConfigurationItem#address
     * @type {string}
     */
    @Serialize(String) address: string;

    /**
     * Замечания
     * @property
     * @name SD.ConfigurationItem#remark
     * @type {string}
     */
    @Serialize(String) remark: string;

    /**
     * ip
     * @property
     * @name SD.ConfigurationItem#ip
     * @type {string}
     */
    @Serialize(String) ip: string;

    /**
     * Цена
     * @property
     * @name SD.ConfigurationItem#price
     * @type {number}
     */
    @Serialize(Number) price: number;

    /**
     * Наличие вложения
     * @property
     * @name SD.ConfigurationItem#attachmentExists
     * @type {boolean}
     */
    @Serialize(Boolean) attachmentExists: boolean;

    /**
     * Идентификатор аватарки
     * @property
     * @name SD.ConfigurationItem#avatarId
     * @type {string}
     */
    @Serialize(String) avatarId: string;

    @Serialize(serializeId) status: any;

    @Serialize(serializeId) category: any;

    toString() {
        return this.searchCode;
    }
}
