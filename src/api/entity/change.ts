import {EntityTypes} from './util/entity-types';

/**
 * Изменение
 * @class
 * @name Change
 * @mixes ENTITY_MIXIN.Historyable
 * @mixes ENTITY_MIXIN.Approvable
 * @mixes ENTITY_MIXIN.AttachmentsHolder
 * @mixes ENTITY_MIXIN.Accessible
 * @extends EditableEntity
*/
export class Change {
    static readonly entityTypeId: EntityTypes = EntityTypes.Change;
    /**
     * Номер
     * @property
     * @name Change#no
     * @type {number}
     */
     no: number;

    /**
     * Тема
     * @property
     * @name Change#subject
     * @type {string}
     */
     subject: string;

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
     * @name Change#solution
     * @type {string}
     */
     solution: string;

    /**
     * Статус
     * @property
     * @name Change#status
     * @type {EntityStatus}
     */
        status;

    /**
     * Приоритет
     * @property
     * @name Change#priority
     * @type {EntityPriority}
     */
        priority;

    /**
     * Категория
     * @property
     * @name Change#category
     * @type {EntityCategory}
     */
     category;

    /**
     * Классификация
     * @property
     * @name Change#classification
     * @type {EntityClassification}
     */
     classification;

    /**
     * Дата создания
     * @property
     * @name Change#createdDate
     * @type {Date}
     */
     createdDate: Date;

    /**
     * Крайний срок
     * @property
     * @name Change#deadline
     * @type {Date}
     */
     deadline: Date;

    /**
     * Реально начато
     * @property
     * @name Change#actualStart
     * @type {Date}
     */
     actualStart: Date;

    /**
     * Дата фактического выполнения
     * @property
     * @name Change#resolvedDate
     * @type {Date}
     */
     resolvedDate: Date;

    /**
     * Дата закрытия
     * @property
     * @name Change#closureDate
     * @type {Date}
     */
     closureDate: Date;

    /**
     * План начала
     * @property
     * @name Change#planStart
     * @type {Date}
     */
     planStart: Date;

    /**
     * План окночания
     * @property
     * @name Change#planFinish
     * @type {Date}
     */
     planFinish: Date;

    /**
     * План продолжительно
     * @property
     * @name Change#planDuration
     * @type {Date}
     */
     planDuration: Date;

    /**
     * Инициатор
     * @property
     * @name Change#person
     * @type {Person}
     */
     initiator;

    /**
     * Менеджер
     * @property
     * @name Change#manager
     * @type {Person}
     */
     manager;

    /**
     * Объект обслуживания
     * @property
     * @name Change#configurationItem
     * @type {ConfigurationItem}
     */
     configurationItem;

    /**
     * Код завершения
     * @property
     * @name Change#closureCode
     * @type {EntityClosureCode}
     */
     closureCode;

    /**
     * Сущность "назначено"
     * @property
     * @name Change#assignment
     * @type {EntityAssignment}
     */
        assignment;

    /**
     * Кастомный код 1(Система)
     * @property
     * @name Change#entityCode1
     * @type {EntityCode1}
     */
     system;

    /**
     * Папка
     * @property
     * @name Change#folder
     * @type {Folder}
     */
     folder;

    toString() {
        return String(this.no);
    }
}
