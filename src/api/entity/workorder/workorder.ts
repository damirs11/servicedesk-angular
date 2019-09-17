import { EntityTypes } from 'src/api/util/entity-types';
import { Entity } from '../entity/entity';
import { Problem } from '../problem/problem';
import { EntityStatus } from '../entity-status/entity-status';
import { EntityCategory } from '../entity-category/entity-category';
import { EntityClosureCode } from '../entity-closure-code/entity-closure-code';
import { Folder } from '../folder/folder';
import { Person } from '../person/person';
import { EntityAssignment } from '../entity-assignment/entity-assignment';
import { Change } from '../change/change';

/**
 * Персона
 * @class
 * @name Workorder
 * @mixes ENTITY_MIXIN.Historyable
 * @mixes ENTITY_MIXIN.Accessible
 * @mixes ENTITY_MIXIN.AttachmentsHolder
 * @extends Entity
 */
export class Workorder extends Entity {
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
  status: EntityStatus;

  /**
   * Категория
   * @property
   * @name Workorder#category
   * @type {EntityCategory}
   */
  category: EntityCategory;

  /**
   * Код завершения
   * @property
   * @name Workorder#closureCode
   * @type {EntityClosureCode}
   */
  closureCode: EntityClosureCode;

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
  folder: Folder;

  /**
   * Инициатор
   * @property
   * @name Workorder#initiator
   * @type {Person}
   */
  initiator: Person;

  /**
   * Объект "Назначено"
   * @property
   * @name Workorder#assignment
   * @type {EntityAssignment}
   */
  assignment: EntityAssignment;

  /**
   * Изменение
   * @property
   * @name Workorder#change
   * @type {Change}
   */
  change: Change;

  /**
   * Проблема
   * @property
   * @name Workorder#problem
   * @type {Problem}
   */
  problem: Problem;

  /**
   * Override
   */
  toString(): string {
    return String(this.no);
  }
}
