import {EntityTypes} from './util/entity-types';

/**
 * Заявка
 * @class
 * @name ServiceCall
 * @mixes ENTITY_MIXIN.Historyable
 * @mixes ENTITY_MIXIN.Accessible
 * @mixes ENTITY_MIXIN.AttachmentsHolder
 * @extends EditableEntity
 */
export class ServiceCall {
    static readonly entityTypeId = EntityTypes.ServiceCall;
    /**
     * Номер
     * @property
     * @name ServiceCall#no
     * @type {number}
     */
        no: number;

    /**
     * Тема
     * @property
     * @name ServiceCall#subject
     * @type {string}
     */
        subject: string;

    /**
     * Ext ID
     * @property
     * @name Change#extId
     * @type {string}
     */
        extId: string;

    /**
     * Описание
     * @property
     * @name Change#description
     * @type {string}
     */
        description: string;

    /**
     * Решение
     * @property
     * @name ServiceCall#solution
     * @type {string}
     */
        solution: string;

    /**
     * Статус
     * @property
     * @name ServiceCall#status
     * @type {EntityStatus}
     */
            status;
    /**
     * Источник
     * @property
     * @name ServiceCall#source
     * @type {Source}
     */
            source;
    /**
     * Время e-mail
     * @property
     * @name ServiceCall#emailDate
     * @type {Date}
     */
        emailDate: Date;
    /**
     * Приоритет
     * @property
     * @name ServiceCall#priority
     * @type {EntityPriority}
     */
            priority;

    /**
     * Категория
     * @property
     * @name ServiceCall#category
     * @type {EntityCategory}
     */
        category;

    /**
     * Классификация
     * @property
     * @name ServiceCall#classification
     * @type {EntityClassification}
     */
        classification;

    /**
     * Крайний срок
     * @property
     * @name ServiceCall#deadline
     * @type {Date}
     */
        deadline: Date;

    /**
     * Дата фактического выполнения
     * @property
     * @name ServiceCall#resolvedDate
     * @type {Date}
     */
        resolvedDate: Date;

    /**
     * Дата закрытия
     * @property
     * @name ServiceCall#closureDate
     * @type {Date}
     */
        closureDate: Date;

    /**
     * Инициатор
     * @property
     * @name ServiceCall#person
     * @type {Person}
     */
        initiator;

    /**
     * Заявитель
     * @property
     * @name ServiceCall#caller
     * @type {Person}
     */
    caller;

    /**
     * Организация
     * @property
     * @name ServiceCall#organization
     * @type {Organization}
     */
    organization;

    /**
     * SLA
     * @property
     * @name ServiceCall#serviceLevelAgreement
     * @type {ServiceLevelAgreement}
     */
    serviceLevelAgreement;

    /**
     * Сервис/услуга
     * @property
     * @name ServiceCall#service
     * @type {Service}
     */
    service;

    /**
     * Объект обслуживания
     * @property
     * @name ServiceCall#configurationItem
     * @type {ConfigurationItem}
     */
    configurationItem;

    /**
     * Код завершения
     * @property
     * @name ServiceCall#closureCode
     * @type {EntityClosureCode}
     */
    closureCode;

    /**
     * Папка
     * @property
     * @name ServiceCall#folder
     * @type {Folder}
     */
    folder;

    /**
     * Сущность "назначено"
     * @property
     * @name ServiceCall#assignment
     * @type {EntityAssignment}
     */
    assignment;

    /**
     * Дата возобновления
     * @property
     * @name ServiceCall#renewalDate
     * @type {Date}
     */
        renewalDate: Date;

    /**
     * Комментарий по приостановке
     * @property
     * @name ServiceCall#renewalReason
     * @type {string}
     */
    renewalComment: string;

    /**
     * Причина приостановки
     * @property
     * @name ServiceCall#renewalReason
     * @type {EntityCode7}
     */
    renewalReason;

    /**
     * Новый крайний срок
     * @property
     * @name ServiceCall#newDeadline
     * @type {Date}
     */
    newDeadline: Date;

    /**
     * Причина переноса крайнего срока
     * @property
     * @name ServiceCall#newDeadlineReason
     * @type {string}
     */
    newDeadlineReason: string;
    /**
     * Нарушение регистрации
     * @property
     * @name ServiceCall#registrationError
     * @type {string}
     */
    registrationError: string;
    /**
     * Часто задаваемые вопросы
     * @property
     * @name ServiceCall#frequentlyAskedQuestion
     * @type {string}
     */
    frequentlyAskedQuestion: string;
    /**
     * База известных ошибок
     * @property
     * @name ServiceCall#faq
     * @type {FAQ}
     */
    faq;
    /**
     * Руководитель исполнителя
     * @property
     * @name ServiceCall#executorHead
     * @type {Person}
     */
    executorHead;

    /**
     * Подсистема АИС ЭАД
     * @property
     * @name ServiceCall#entityCode6
     * @type {EntityCode6}
     */
    entityCode6;



    toString() {
        return String(this.no);
    }
}
