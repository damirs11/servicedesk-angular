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

/** Персона */
export class Workorder extends Entity {
  static readonly entityTypeId: EntityTypes = EntityTypes.Workorder;
  /** Номер */
  public no: number;

  /** Тема */
  public subject: string;

  /** Подробная информация */
  public description: string;

  /** Трудозатраты */
  public labor: number;

  /** Решение */
  public solution: string;

  /** Статус */
  public status: EntityStatus;

  /** Категория */
  public category: EntityCategory;

  /** Код завершения */
  public closureCode: EntityClosureCode;

  /** Дата создания */
  public createdDate: Date;

  /** Крайний срок */
  public deadline: Date;

  /** Фактически выполнено */
  public resolvedDate: Date;

  /** Дата изменения */
  public modifyDate: Date;

  /** Наряд просрочен */
  public expired: boolean;

  /** Папка */
  public folder: Folder;

  /** Инициатор */
  public initiator: Person;

  /** Объект "Назначено" */
  public assignment: EntityAssignment;

  /** Изменение */
  public change: Change;

  /** Проблема */
  public problem: Problem;

  /** @Override */
  toString(): string {
    return String(this.no);
  }
}
