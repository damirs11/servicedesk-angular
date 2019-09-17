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
   * @type {String}
   */
  subject: string;

  /**
   * Подробная информация
   * @type {String}
   */
  description: string;

  /**
   * Трудозатраты
   * @type {Number}
   */
  labor: number;

  /**
   * Решение
   * @type {String}
   */
  solution: string;

  /**
   * Статус
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Категория
   * @type {EntityCategory}
   */
  category: EntityCategory;

  /**
   * Код завершения
   * @type {EntityClosureCode}
   */
  closureCode: EntityClosureCode;

  /**
   * Дата создания
   * @type {Date}
   */
  createdDate: Date;

  /**
   * Крайний срок
   * @type {Date}
   */
  deadline: Date;

  /**
   * Фактически выполнено
   * @type {Date}
   */
  resolvedDate: Date;

  /**
   * Дата изменения
   * @type {Date}
   */
  modifyDate: Date;

  /**
   * Наряд просрочен
   * @type {Boolean}
   */
  expired: boolean;

  /**
   * Папка
   * @type {Folder}
   */
  folder: Folder;

  /**
   * Инициатор
   * @type {Person}
   */
  initiator: Person;

  /**
   * Объект "Назначено"
   * @type {EntityAssignment}
   */
  assignment: EntityAssignment;

  /**
   * Изменение
   * @type {Change}
   */
  change: Change;

  /**
   * Проблема
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
