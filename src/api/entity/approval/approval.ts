import { Entity } from "../entity/entity";
import { Workgroup } from "../workgroup/workgroup";
import { EntityStatus } from "../entity-status/entity-status";
import { EntityTypes } from "src/api/util/entity-types";
import { Person } from '../person/person';

/**
 * Голосование
 * @class
 */
export class Approval extends Entity {
  static readonly entityTypeId: EntityTypes = EntityTypes.Approval;
  /**
   * ID сущности
   * @property
   * @type {String}
   */
  subject: string;

  /**
   * Необходимое количество поддержавших
   * @property
   * @type {Number}
   */
  numberOfApproversRequired: number;

  /**
   * Количество согласующих
   * @property
   * @type {Number}
   */
  numberOfApprovers: number;

  /**
   * Количество одобривших
   * @property
   * @type {Number}
   */
  numberOfApproversApproved: number;

  /**
   * Группа согласования
   * @property
   * @type {Workgroup}
   */
  workgroup: Workgroup;

  /**
   * Статус
   * @property
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Крайний срок
   * @property
   * @type {Date}
   */
  deadline: Date;

  /**
   * Идентификатор сущности,
   * которой принадлежит согласование
   * @property
   * @type {Number}
   * @ATTENTION
   * Т.к. это поле должно отправляться при сохранении всегда,
   * метод сериализации не используются. Оно добавляется
   * в переопределенном методе save
   * @link save
   * @link Approval#save
   */
  ownerEntityType: number;

  /**
   * Инициатор согласования
   * @property
   * @type {Person}
   */
  initiator: Person;
}
