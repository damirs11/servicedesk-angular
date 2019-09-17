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
 */
export class Workorder extends Entity {
  static readonly entityTypeId: EntityTypes = EntityTypes.Workorder;
  /**
   * Номер
   * @property
   * @type {number}
   */
  no: number;

  /**
   * Тема
   * @property
   * @type {String}
   */
  subject: string;

  /**
   * Подробная информация
   * @property
   * @type {String}
   */
  description: string;

  /**
   * Трудозатраты
   * @property
   * @type {Number}
   */
  labor: number;

  /**
   * Решение
   * @property
   * @type {String}
   */
  solution: string;

  /**
   * Статус
   * @property
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Категория
   * @property
   * @type {EntityCategory}
   */
  category: EntityCategory;

  /**
   * Код завершения
   * @property
   * @type {EntityClosureCode}
   */
  closureCode: EntityClosureCode;

  /**
   * Дата создания
   * @property
   * @type {Date}
   */
  createdDate: Date;

  /**
   * Крайний срок
   * @property
   * @type {Date}
   */
  deadline: Date;

  /**
   * Фактически выполнено
   * @property
   * @type {Date}
   */
  resolvedDate: Date;

  /**
   * Дата изменения
   * @property
   * @type {Date}
   */
  modifyDate: Date;

  /**
   * Наряд просрочен
   * @property
   * @type {Boolean}
   */
  expired: boolean;

  /**
   * Папка
   * @property
   * @type {Folder}
   */
  folder: Folder;

  /**
   * Инициатор
   * @property
   * @type {Person}
   */
  initiator: Person;

  /**
   * Объект "Назначено"
   * @property
   * @type {EntityAssignment}
   */
  assignment: EntityAssignment;

  /**
   * Изменение
   * @property
   * @type {Change}
   */
  change: Change;

  /**
   * Проблема
   * @property
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
