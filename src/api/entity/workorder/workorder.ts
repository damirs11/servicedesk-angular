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
   */
  no: number;

  /**
   * Тема
   */
  subject: string;

  /**
   * Подробная информация
   */
  description: string;

  /**
   * Трудозатраты
   */
  labor: number;

  /**
   * Решение
   */
  solution: string;

  /**
   * Статус
   */
  status: EntityStatus;

  /**
   * Категория
   */
  category: EntityCategory;

  /**
   * Код завершения
   */
  closureCode: EntityClosureCode;

  /**
   * Дата создания
   */
  createdDate: Date;

  /**
   * Крайний срок
   */
  deadline: Date;

  /**
   * Фактически выполнено
   */
  resolvedDate: Date;

  /**
   * Дата изменения
   */
  modifyDate: Date;

  /**
   * Наряд просрочен
   */
  expired: boolean;

  /**
   * Папка
   */
  folder: Folder;

  /**
   * Инициатор
   */
  initiator: Person;

  /**
   * Объект "Назначено"
   */
  assignment: EntityAssignment;

  /**
   * Изменение
   */
  change: Change;

  /**
   * Проблема
   */
  problem: Problem;

  /** @Override */
  toString(): string {
    return String(this.no);
  }
}
