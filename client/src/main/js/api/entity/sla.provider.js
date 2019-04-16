import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Nullable} from "./decorator/parse-utils";
import {serializeId} from "./decorator/serialize-utils";

SLAProvider.$inject = ["SD", "RESTEntity"];
function SLAProvider(SD, RESTEntity) {
    /**
     * SLA (условие предоставления услуги)
     * @class
     * @name SD.SLA
     */
    class SLA extends RESTEntity {

        /**
         * Сервис/услуга
         * @property
         * @name SD.SLA#service
         * @type {SD.Service}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.Service.parse(data)) service;

        /**
         * Статус
         * @property
         * @name SD.SLA#status
         * @type {SD.EntityStatus}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityStatus.parse(data)) status;

        /**
         * ?
         * @property
         * @name SD.SLA#agreementCondition
         * @type {number}
         */
        @Serialize(Number) @Parse(Number) agreementCondition;

        /**
         * Название
         * @property
         * @name SD.SLA#name
         * @type {string}
         */
        @Parse(String) name;

        /**
         * Папка
         * @property
         * @name SD.SLA#folder
         * @type {SD.Folder}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.Folder.parse(data)) folder;

        /**
         * Срок действия от
         * @property
         * @name SD.SLA#validFrom
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) validFrom;

        /**
         * Срок действия до
         * @property
         * @name SD.SLA#validTo
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) validTo;

        toString(){
            return String(this.name);
        }

    }
    return SLA;
}

export {SLAProvider};