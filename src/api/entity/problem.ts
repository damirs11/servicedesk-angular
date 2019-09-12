import {EntityTypes} from './util/entity-types';

/**
 * Сущность - "Проблема"
 * @class
 * @name Problem
 * @mixes ENTITY_MIXIN.Historyable
 * @mixes ENTITY_MIXIN.Approvable
 * @mixes ENTITY_MIXIN.AttachmentsHolder
 * @mixes ENTITY_MIXIN.Accessible
 * @extends EditableEntity
 */
class Problem {
    static readonly entityTypeId:EntityTypes = EntityTypes.Problem;
    /**
     * Номер
     * @property
     * @name Problem#no
     * @type {number}
     */
     no: number;

    /**
     * Статус
     * @property
     * @name Problem#status
     * @type {EntityStatus}
     */
        status;

    /**
     * Инициатор
     * @property
     * @name Problem#initiator
     * @type {Person}
     */
     initiator;

    /**
     * Объект обслуживания
     * @property
     * @name Problem#configurationItem
     * @type {ConfigurationItem}
     */
     configurationItem;

    /**
     * Тема
     * @property
     * @name Problem#subject
     * @type {string}
     */
     subject: string;

    /**
     * Описание
     * @property
     * @name Problem#description
     * @type {string}
     */
     description: string;

    /**
     * Ссылки на логи
     * @property
     * @name Problem#logLinks
     * @type {string}
     */
     logLinks: string;

    /**
     * Ссылка на пробелму в jira
     * @property
     * @name Problem#jiraLink
     * @type {string}
     */
     jiraLink: string;
    /**
     * @property
     * @name Problem#toVendor
     * @type {string}
     */
     toVendor: string;
    /**
     * Обходное решение
     * @property
     * @name Problem#workaround
     * @type {string}
     */
     workaround: string;

    /**
     * Решение
     * @property
     * @name Problem#solution
     * @type {string}
     */
     solution: string;

    /**
     * Приоритет
     * @property
     * @name Problem#priority
     * @type {EntityPriority}
     */
        priority;

    /**
     * Крайний срок
     * @property
     * @name Problem#deadline
     * @type {Date}
     */
     deadline: Date;

    /**
     * Дата фактического выполнения
     * @property
     * @name Problem#resolvedDate
     * @type {Date}
     */
     resolvedDate: Date;

    /**
     * Дата закрытия
     * @property
     * @name Problem#closureDate
     * @type {Date}
     */
     closureDate: Date;

    /**
     * Проблема просрочена
     * @property
     * @name Problem#isOverdue
     * @type {Date}
     */
     isOverdue: Date;

    /**
     * Персона, что просрочила проблему
     * @property
     * @name Problem#whoOverdue
     * @type {Person}
     */
     whoOverdue;

    /**
     * План окночания
     * @property
     * @name Problem#planFinish
     * @type {Date}
     */
     planFinish: Date;

    /**
     * Причина отсрочки
     * @property
     * @name Problem#deferralReason
     * @type {string}
     */
     deferralReason: string;

    /**
     * Сущность "назначено"
     * @property
     * @name Problem#assignment
     * @type {EntityAssignment}
     */
        assignment;

    /**
     * Категория
     * @property
     * @name Problem#category
     * @type {EntityCategory}
     */
     category;

    /**
     * Классификация
     * @property
     * @name Problem#classification
     * @type {EntityClassification}
     */
     classification;

    /**
     * Код завершения
     * @property
     * @name Problem#closureCode
     * @type {EntityClosureCode}
     */
     closureCode;

    /**
     * Папка
     * @property
     * @name Problem#folder
     * @type {Folder}
     */
     folder;

    /**
     * Не включать в отчет заказчику
     * @property
     * @name Problem#isOverdue
     * @type {Date}
     */
     notAttachInReport: Date;

    /**
     *
     * @property
     * @name Problem#versionDate
     * @type {Date}
     */
     versionDate: Date;

    toString() {
        return String(this.no);
    }

}
