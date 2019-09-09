import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Nullable} from "./decorator/parse-utils";
import {serializeId} from "./decorator/serialize-utils";

SLAProvider.$inject = ["SD", "RESTEntity"];
function SLAProvider(SD, RESTEntity) {
    /**
     * SLA (условие предоставления услуги)
     * @class
     * @name SD.ServiceLevelAgreement
     */
    class ServiceLevelAgreement extends RESTEntity {

        /**
         * Сервис/услуга
         * @property
         * @name SD.ServiceLevelAgreement#service
         * @type {SD.Service}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.Service.parse(data)) service;

        /**
         * Статус
         * @property
         * @name SD.ServiceLevelAgreement#status
         * @type {SD.EntityStatus}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityStatus.parse(data)) status;

        /**
         * Название
         * @property
         * @name SD.ServiceLevelAgreement#name
         * @type {string}
         */
        @Parse(String) name;

        /**
         * Папка
         * @property
         * @name SD.ServiceLevelAgreement#folder
         * @type {SD.Folder}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.Folder.parse(data)) folder;

        /**
         * Срок действия от
         * @property
         * @name SD.ServiceLevelAgreement#validFrom
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) validFrom;

        /**
         * Срок действия до
         * @property
         * @name SD.ServiceLevelAgreement#validTo
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) validTo;

        /**
         * Исполнитель по умолчанию
         * @property
         * @name SD.ServiceLevelAgreement#person
         * @type {object}
         */
        @Serialize(serializeId) @Parse(data => SD.Person.parse(data))
        person;

        /**
         * Группа исполнителей по умолчанию
         * @property
         * @name SD.ServiceLevelAgreement#workgroup
         * @type {SD.Workgroup}
         */
        @Serialize(Nullable(serializeId))
        @Parse(data => SD.Workgroup.parse(data) )
        workgroup;

        /**
         * Условия предоставления
         * @property
         * @name SD.ServiceLevelAgreement#serviceLevel
         * @type {SD.Workgroup}
         */
        @Serialize(Nullable(serializeId))
        @Parse(data => SD.ServiceLevel.parse(data) )
        serviceLevel;
        /**
         * Приоритет по умолчанию
         * @property
         * @name SD.ServiceLevelAgreement#defaultPriority
         * @type {SD.EntityPriority}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityPriority.parse(data)) defaultPriority;
        toString(){
            return String(this.name);
        }

    }
    return ServiceLevelAgreement;
}

export {SLAProvider};