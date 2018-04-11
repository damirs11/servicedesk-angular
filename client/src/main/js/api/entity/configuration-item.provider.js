import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Nullable} from "./decorator/parse-utils";
import {serializeId} from "./decorator/serialize-utils";
import {Mixin} from "./mixin/mixin.decorator";


ConfigurationItemProvider.$inject = ["EditableEntity", "SD"];
function ConfigurationItemProvider(EditableEntity, SD) {
    /**
     * Объект обслуживания
     * @class
     * @name SD.ConfigurationItem
     * @extends SD.EditableEntity
     */
    class ConfigurationItem extends EditableEntity {
        static $entityTypeId = 796000260;
        /**
         * Номер
         * @property
         * @name SD.ConfigurationItem#no
         * @type {number}
         */
        @Serialize(Number) @Parse(Number) no;

        /**
         * Код поиска
         * @property
         * @name SD.ConfigurationItem#searchCode
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) searchCode;

        /**
         * Название
         * @property
         * @name SD.ConfigurationItem#name
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) name;

        /**
         * Дополнительные сведения
         * @property
         * @name SD.ConfigurationItem#description
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) description;

        /**
         * Счет-фактура
         * @property
         * @name SD.ConfigurationItem#orderNr
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) orderNr;

        /**
         * серийный номер
         * @property
         * @name SD.ConfigurationItem#serial
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) serial;

        /**
         * Адрес
         * @property
         * @name SD.ConfigurationItem#address
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) address;

        /**
         * Замечания
         * @property
         * @name SD.ConfigurationItem#remark
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) remark;

        /**
         * ip
         * @property
         * @name SD.ConfigurationItem#ip
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) ip;

        /**
         * Цена
         * @property
         * @name SD.ConfigurationItem#price
         * @type {number}
         */
        @Serialize(Number) @Parse(Nullable(Number)) price;

        /**
         * Наличие вложения
         * @property
         * @name SD.ConfigurationItem#attachmentExists
         * @type {boolean}
         */
        @Serialize(Boolean) @Parse(Nullable(Boolean)) attachmentExists;

        /**
         * Идентификатор аватарки
         * @property
         * @name SD.ConfigurationItem#avatarId
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) avatarId;

        @Serialize(serializeId) @Parse(data => SD.EntityStatus.parse(data)) status;

        @Serialize(serializeId) @Parse(data => SD.EntityCategory.parse(data)) category;

        toString(){
            return this.searchCode;
        }

    }
    return ConfigurationItem
}

export {ConfigurationItemProvider};