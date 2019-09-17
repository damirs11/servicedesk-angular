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
   * @type {Number}
   */
  numberOfApproversRequired: number;

  /**
   * Количество согласующих
   * @type {Number}
   */
  numberOfApprovers: number;

  /**
   * Количество одобривших
   * @type {Number}
   */
  numberOfApproversApproved: number;

  /**
   * Группа согласования
   * @type {Workgroup}
   */
  workgroup: Workgroup;

  /**
   * Статус
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Крайний срок
   * @type {Date}
   */
  deadline: Date;

  /**
   * Идентификатор сущности,
   * которой принадлежит согласование
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
   * @type {Person}
   */
  initiator: Person;
}
