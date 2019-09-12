import {EntityTypes} from './util/entity-types';

/**
 * Персона
 * @class
 * @name Workorder
 * @mixes ENTITY_MIXIN.Historyable
 * @mixes ENTITY_MIXIN.Accessible
 * @mixes ENTITY_MIXIN.AttachmentsHolder
 * @extends EditableEntity
 */
export class Workorder {
    static readonly entityTypeId: EntityTypes = EntityTypes.Workorder;
    /**
     * Номер
     * @property
     * @name Workorder#no
     * @type {number}
     */
     no: number;

    /**
     * Тема
     * @property
     * @name Workorder#subject
     * @type {String}
     */
      subject: string;

    /**
     * Подробная информация
     * @property
     * @name Workorder#description
     * @type {String}
     */
     description: string;

    /**
     * Трудозатраты
     * @property
     * @name Workorder#labor
     * @type {Number}
     */
     labor: number;

    /**
     * Решение
     * @property
     * @name Workorder#solution
     * @type {String}
     */
     solution: string;

    /**
     * Статус
     * @property
     * @name Workorder#status
     * @type {EntityStatus}
     */
     status;

    /**
     * Категория
     * @property
     * @name Workorder#category
     * @type {EntityCategory}
     */
      category;

    /**
     * Код завершения
     * @property
     * @name Workorder#closureCode
     * @type {EntityClosureCode}
     */
      closureCode;

    /**
     * Дата создания
     * @property
     * @name Workorder#createdDate
     * @type {Date}
     */
      createdDate: Date;

    /**
     * Крайний срок
     * @property
     * @name Workorder#deadline
     * @type {Date}
     */
      deadline: Date;

    /**
     * Фактически выполнено
     * @property
     * @name Workorder#resolvedDate
     * @type {Date}
     */
      resolvedDate: Date;

    /**
     * Дата изменения
     * @property
     * @name Workorder#modifyDate
     * @type {Date}
     */
      modifyDate: Date;

    /**
     * Наряд просрочен
     * @property
     * @name Workorder#expired
     * @type {Boolean}
     */
      expired: boolean;

    /**
     * Папка
     * @property
     * @name Workorder#folder
     * @type {Folder}
     */
      folder;

    /**
     * Инициатор
     * @property
     * @name Workorder#initiator
     * @type {Person}
     */
      initiator;

    /**
     * Объект "Назначено"
     * @property
     * @name Workorder#assignment
     * @type {EntityAssignment}
     */
         assignment;

    /**
     * Изменение
     * @property
     * @name Workorder#change
     * @type {Change}
     */
      change;

    /**
     * Проблема
     * @property
     * @name Workorder#problem
     * @type {Problem}
     */
      problem;

    toString() {
        return String(this.no);
    }
}
