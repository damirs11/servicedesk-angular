/**
 * Объект обслуживания
 * @class
 * @name ConfigurationItem
 * @extends EditableEntity
 */
export class ConfigurationItem {
    static $entityTypeId = 796000260;
    /**
     * Номер
     * @property
     * @name ConfigurationItem#no
     * @type {number}
     */
     no: number;

    /**
     * Код поиска
     * @property
     * @name ConfigurationItem#searchCode
     * @type {string}
     */
     searchCode: string;

    /**
     * Название
     * @property
     * @name ConfigurationItem#name
     * @type {string}
     */
     name: string;

    /**
     * Дополнительные сведения
     * @property
     * @name ConfigurationItem#description
     * @type {string}
     */
     description: string;

    /**
     * Счет-фактура
     * @property
     * @name ConfigurationItem#orderNr
     * @type {string}
     */
     orderNr: string;

    /**
     * серийный номер
     * @property
     * @name ConfigurationItem#serial
     * @type {string}
     */
     serial: string;

    /**
     * Адрес
     * @property
     * @name ConfigurationItem#address
     * @type {string}
     */
     address: string;

    /**
     * Замечания
     * @property
     * @name ConfigurationItem#remark
     * @type {string}
     */
     remark: string;

    /**
     * ip
     * @property
     * @name ConfigurationItem#ip
     * @type {string}
     */
     ip: string;

    /**
     * Цена
     * @property
     * @name ConfigurationItem#price
     * @type {number}
     */
     price: number;

    /**
     * Наличие вложения
     * @property
     * @name ConfigurationItem#attachmentExists
     * @type {boolean}
     */
     attachmentExists: boolean;

    /**
     * Идентификатор аватарки
     * @property
     * @name ConfigurationItem#avatarId
     * @type {string}
     */
     avatarId: string;

     status: any;

     category: any;

    toString() {
        return this.searchCode;
    }
}
