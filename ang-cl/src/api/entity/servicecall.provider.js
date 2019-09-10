import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Nullable} from "./decorator/parse-utils";
import {serializeId} from "./decorator/serialize-utils";
import {Mixin} from "./mixin/mixin.decorator";
import {EntityTypes} from "./util/entity-types";

ServiceCallProvider.$inject = ["EditableEntity", "SD", "Historyable", "Accessible", "AttachmentsHolder"];
function ServiceCallProvider(EditableEntity, SD, Historyable, Accessible, AttachmentsHolder) {
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
    class ServiceCall extends EditableEntity {
        static $entityTypeId = EntityTypes.ServiceCall;
        /**
         * Номер
         * @property
         * @name SD.ServiceCall#no
         * @type {number}
         */
        @Serialize(Number) @Parse(Number) no;

        /**
         * Тема
         * @property
         * @name SD.ServiceCall#subject
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) subject;

        /**
         * Ext ID
         * @property
         * @name SD.Change#extId
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) extId;

        /**
         * Описание
         * @property
         * @name SD.Change#description
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) description;

        /**
         * Решение
         * @property
         * @name SD.ServiceCall#solution
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) solution;

        /**
         * Статус
         * @property
         * @name SD.ServiceCall#status
         * @type {SD.EntityStatus}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityStatus.parse(data)) status;
        /**
         * Источник
         * @property
         * @name SD.ServiceCall#source
         * @type {SD.Source}
         */
        @Serialize(serializeId)
        @Parse(data => SD.Source.parse(data)) source;
        /**
         * Время e-mail
         * @property
         * @name SD.ServiceCall#emailDate
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) emailDate;
        /**
         * Приоритет
         * @property
         * @name SD.ServiceCall#priority
         * @type {SD.EntityPriority}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityPriority.parse(data)) priority;

        /**
         * Категория
         * @property
         * @name SD.ServiceCall#category
         * @type {SD.EntityCategory}
         */
        @Serialize(serializeId) @Parse(data => SD.EntityCategory.parse(data)) category;

        /**
         * Классификация
         * @property
         * @name SD.ServiceCall#classification
         * @type {SD.EntityClassification}
         */
        @Serialize(serializeId) @Parse(data => SD.EntityClassification.parse(data)) classification;

        /**
         * Крайний срок
         * @property
         * @name SD.ServiceCall#deadline
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) deadline;

        /**
         * Дата фактического выполнения
         * @property
         * @name SD.ServiceCall#resolvedDate
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) resolvedDate;

        /**
         * Дата закрытия
         * @property
         * @name SD.ServiceCall#closureDate
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) closureDate;

        /**
         * Инициатор
         * @property
         * @name SD.ServiceCall#person
         * @type {SD.Person}
         */
        @Serialize(serializeId) @Parse(data => SD.Person.parse(data)) initiator;

        /**
         * Заявитель
         * @property
         * @name SD.ServiceCall#caller
         * @type {SD.Person}
         */
        @Serialize(serializeId) @Parse(data => SD.Person.parse(data)) caller;

        /**
         * Организация
         * @property
         * @name SD.ServiceCall#organization
         * @type {SD.Organization}
         */
        @Serialize(serializeId) @Parse(data => SD.Organization.parse(data)) organization;

        /**
         * SLA
         * @property
         * @name SD.ServiceCall#serviceLevelAgreement
         * @type {SD.ServiceLevelAgreement}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.ServiceLevelAgreement.parse(data)) serviceLevelAgreement;

        /**
         * Сервис/услуга
         * @property
         * @name SD.ServiceCall#service
         * @type {SD.Service}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.Service.parse(data)) service;

        /**
         * Объект обслуживания
         * @property
         * @name SD.ServiceCall#configurationItem
         * @type {SD.ConfigurationItem}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.ConfigurationItem.parse(data)) configurationItem;

        /**
         * Код завершения
         * @property
         * @name SD.ServiceCall#closureCode
         * @type {SD.EntityClosureCode}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.EntityClosureCode.parse(data)) closureCode;

        /**
         * Папка
         * @property
         * @name SD.ServiceCall#folder
         * @type {SD.Folder}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.Folder.parse(data)) folder;
        
        /**
         * Сущность "назначено"
         * @property
         * @name SD.ServiceCall#assignment
         * @type {SD.EntityAssignment}
         */
        @Serialize((ag,name,mode) => mode === "FULL" ? ag.$serialize() : ag.$modifiedData)
        @Parse(data => SD.EntityAssignment.parse(data)) assignment;

        /**
         * Дата возобновления
         * @property
         * @name SD.ServiceCall#renewalDate
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) renewalDate;

        /**
         * Комментарий по приостановке
         * @property
         * @name SD.ServiceCall#renewalReason
         * @type {string}
         */
        @Parse(String) renewalComment;

        /**
         * Причина приостановки
         * @property
         * @name SD.ServiceCall#renewalReason
         * @type {SD.EntityCode7}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.EntityCode7.parse(data)) renewalReason;

        /**
         * Новый крайний срок
         * @property
         * @name SD.ServiceCall#newDeadline
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date) ) newDeadline;

        /**
         * Причина переноса крайнего срока
         * @property
         * @name SD.ServiceCall#newDeadlineReason
         * @type {string}
         */
        @Parse(String) newDeadlineReason;
        /**
         * Нарушение регистрации
         * @property
         * @name SD.ServiceCall#registrationError
         * @type {string}
         */
        @Parse(Boolean) registrationError;
        /**
         * Часто задаваемые вопросы
         * @property
         * @name SD.ServiceCall#frequentlyAskedQuestion
         * @type {string}
         */
        @Parse(Boolean) frequentlyAskedQuestion;
        /**
         * База известных ошибок
         * @property
         * @name SD.ServiceCall#faq
         * @type {SD.FAQ}
         */
        @Serialize(serializeId)
        @Parse(data => SD.FAQ.parse(data)) faq;
        /**
         * Руководитель исполнителя
         * @property
         * @name SD.ServiceCall#executorHead
         * @type {SD.Person}
         */
        @Serialize(serializeId) @Parse(data => SD.Person.parse(data)) executorHead;

        /**
         * Подсистема АИС ЭАД
         * @property
         * @name SD.ServiceCall#entityCode6
         * @type {SD.EntityCode6}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityCode6.parse(data)) entityCode6;

        toString(){
            return String(this.no);
        }

    }
    return ServiceCall;
}

export {ServiceCallProvider};