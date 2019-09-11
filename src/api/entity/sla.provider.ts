import {Serialize} from './decorator/serialize.decorator';
import {Nullable} from './decorator/parse-utils';
import {serializeId} from './decorator/serialize-utils';

/**
 * SLA (условие предоставления услуги)
 * @class
 * @name SD.ServiceLevelAgreement
 */
export class ServiceLevelAgreement {

    /**
         * Сервис/услуга
         * @property
         * @name SD.ServiceLevelAgreement#service
         * @type {SD.Service}
         */
        @Serialize(Nullable(serializeId)) service;

        /**
         * Статус
         * @property
         * @name SD.ServiceLevelAgreement#status
         * @type {SD.EntityStatus}
         */
        @Serialize(serializeId)
        status;

        /**
         * Название
         * @property
         * @name SD.ServiceLevelAgreement#name
         * @type {string}
         */
        name: string;

        /**
         * Папка
         * @property
         * @name SD.ServiceLevelAgreement#folder
         * @type {SD.Folder}
         */
        @Serialize(Nullable(serializeId)) folder;

        /**
         * Срок действия от
         * @property
         * @name SD.ServiceLevelAgreement#validFrom
         * @type {Date}
         */
        @Serialize(Number) validFrom: Date;

        /**
         * Срок действия до
         * @property
         * @name SD.ServiceLevelAgreement#validTo
         * @type {Date}
         */
        @Serialize(Number) validTo: Date;

        /**
         * Исполнитель по умолчанию
         * @property
         * @name SD.ServiceLevelAgreement#person
         * @type {object}
         */
        person: object;

        /**
         * Группа исполнителей по умолчанию
         * @property
         * @name SD.ServiceLevelAgreement#workgroup
         * @type {SD.Workgroup}
         */
        @Serialize(Nullable(serializeId))
        workgroup;

        /**
         * Условия предоставления
         * @property
         * @name SD.ServiceLevelAgreement#serviceLevel
         * @type {SD.Workgroup}
         */
        @Serialize(Nullable(serializeId))
        serviceLevel;
        /**
         * Приоритет по умолчанию
         * @property
         * @name SD.ServiceLevelAgreement#defaultPriority
         * @type {SD.EntityPriority}
         */
        @Serialize(serializeId)
        defaultPriority;
        toString() {
            return String(this.name);
        }
}
