import {Serialize} from './decorator/serialize.decorator';
import {Nullable} from './decorator/parse-utils';
import {serializeId} from './decorator/serialize-utils';
import {Mixin} from './mixin/mixin.decorator';
import {EntityTypes} from './util/entity-types';

/**
 * Заявка
 * @class
 * @name SD.ServiceCall
 * @mixes ENTITY_MIXIN.Historyable
 * @mixes ENTITY_MIXIN.Accessible
 * @mixes ENTITY_MIXIN.AttachmentsHolder
 * @extends SD.EditableEntity
 */
@Mixin(Historyable, Accessible, AttachmentsHolder)
export class ServiceCall {
    static $entityTypeId = EntityTypes.ServiceCall;
    /**
         * Номер
         * @property
         * @name SD.ServiceCall#no
         * @type {number}
         */
        @Serialize(Number) no: number;

        /**
         * Тема
         * @property
         * @name SD.ServiceCall#subject
         * @type {string}
         */
        @Serialize(String) subject: string;

        /**
         * Ext ID
         * @property
         * @name SD.Change#extId
         * @type {string}
         */
        @Serialize(String) extId: string;

        /**
         * Описание
         * @property
         * @name SD.Change#description
         * @type {string}
         */
        @Serialize(String) description: string;

        /**
         * Решение
         * @property
         * @name SD.ServiceCall#solution
         * @type {string}
         */
        @Serialize(String) solution: string;

        /**
         * Статус
         * @property
         * @name SD.ServiceCall#status
         * @type {SD.EntityStatus}
         */
        @Serialize(serializeId)
        status;
        /**
         * Источник
         * @property
         * @name SD.ServiceCall#source
         * @type {SD.Source}
         */
        @Serialize(serializeId)
        source;
        /**
         * Время e-mail
         * @property
         * @name SD.ServiceCall#emailDate
         * @type {Date}
         */
        @Serialize(Number) emailDate: Date;
        /**
         * Приоритет
         * @property
         * @name SD.ServiceCall#priority
         * @type {SD.EntityPriority}
         */
        @Serialize(serializeId)
        priority;

        /**
         * Категория
         * @property
         * @name SD.ServiceCall#category
         * @type {SD.EntityCategory}
         */
        @Serialize(serializeId) category;

        /**
         * Классификация
         * @property
         * @name SD.ServiceCall#classification
         * @type {SD.EntityClassification}
         */
        @Serialize(serializeId) classification;

        /**
         * Крайний срок
         * @property
         * @name SD.ServiceCall#deadline
         * @type {Date}
         */
        @Serialize(Number) deadline: Date;

        /**
         * Дата фактического выполнения
         * @property
         * @name SD.ServiceCall#resolvedDate
         * @type {Date}
         */
        @Serialize(Number) resolvedDate: Date;

        /**
         * Дата закрытия
         * @property
         * @name SD.ServiceCall#closureDate
         * @type {Date}
         */
        @Serialize(Number) closureDate: Date;

        /**
         * Инициатор
         * @property
         * @name SD.ServiceCall#person
         * @type {SD.Person}
         */
        @Serialize(serializeId) initiator;

        /**
         * Заявитель
         * @property
         * @name SD.ServiceCall#caller
         * @type {SD.Person}
         */
        @Serialize(serializeId) caller;

        /**
         * Организация
         * @property
         * @name SD.ServiceCall#organization
         * @type {SD.Organization}
         */
        @Serialize(serializeId) organization;

        /**
         * SLA
         * @property
         * @name SD.ServiceCall#serviceLevelAgreement
         * @type {SD.ServiceLevelAgreement}
         */
        @Serialize(Nullable(serializeId)) serviceLevelAgreement;

        /**
         * Сервис/услуга
         * @property
         * @name SD.ServiceCall#service
         * @type {SD.Service}
         */
        @Serialize(Nullable(serializeId)) service;

        /**
         * Объект обслуживания
         * @property
         * @name SD.ServiceCall#configurationItem
         * @type {SD.ConfigurationItem}
         */
        @Serialize(Nullable(serializeId)) configurationItem;

        /**
         * Код завершения
         * @property
         * @name SD.ServiceCall#closureCode
         * @type {SD.EntityClosureCode}
         */
        @Serialize(Nullable(serializeId)) closureCode;

        /**
         * Папка
         * @property
         * @name SD.ServiceCall#folder
         * @type {SD.Folder}
         */
        @Serialize(Nullable(serializeId)) folder;

        /**
         * Сущность "назначено"
         * @property
         * @name SD.ServiceCall#assignment
         * @type {SD.EntityAssignment}
         */
        @Serialize((ag, name, mode) => mode === 'FULL' ? ag.$serialize() : ag.$modifiedData)
        assignment;

        /**
         * Дата возобновления
         * @property
         * @name SD.ServiceCall#renewalDate
         * @type {Date}
         */
        @Serialize(Number) renewalDate: Date;

        /**
         * Комментарий по приостановке
         * @property
         * @name SD.ServiceCall#renewalReason
         * @type {string}
         */
        renewalComment: string;

        /**
         * Причина приостановки
         * @property
         * @name SD.ServiceCall#renewalReason
         * @type {SD.EntityCode7}
         */
        @Serialize(Nullable(serializeId)) renewalReason;

        /**
         * Новый крайний срок
         * @property
         * @name SD.ServiceCall#newDeadline
         * @type {Date}
         */
        @Serialize(Number) newDeadline: Date;

        /**
         * Причина переноса крайнего срока
         * @property
         * @name SD.ServiceCall#newDeadlineReason
         * @type {string}
         */
        newDeadlineReason: string;
        /**
         * Нарушение регистрации
         * @property
         * @name SD.ServiceCall#registrationError
         * @type {string}
         */
        registrationError: string;
        /**
         * Часто задаваемые вопросы
         * @property
         * @name SD.ServiceCall#frequentlyAskedQuestion
         * @type {string}
         */
        frequentlyAskedQuestion: string;
        /**
         * База известных ошибок
         * @property
         * @name SD.ServiceCall#faq
         * @type {SD.FAQ}
         */
        @Serialize(serializeId)
        faq;
        /**
         * Руководитель исполнителя
         * @property
         * @name SD.ServiceCall#executorHead
         * @type {SD.Person}
         */
        @Serialize(serializeId) executorHead;

        /**
         * Подсистема АИС ЭАД
         * @property
         * @name SD.ServiceCall#entityCode6
         * @type {SD.EntityCode6}
         */
        @Serialize(serializeId)
        entityCode6;



    toString() {
        return String(this.no);
    }
}
