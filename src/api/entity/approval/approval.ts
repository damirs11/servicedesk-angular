import { Entity } from "../entity/entity";
import { Workgroup } from "../workgroup/workgroup";
import { EntityStatus } from "../entity-status/entity-status";
import { EntityTypes } from "src/api/util/entity-types";
import { Person } from '../person/person';

/**
 * Голосование
 * @class
 * @extends Entity
 * @name Approval
 */
export class Approval extends Entity {
  static readonly entityTypeId: EntityTypes = EntityTypes.Approval;
  /**
   * ID сущности
   * @property
   * @name Approval#approvalDescription
   * @type {String}
   */
  subject: string;

  /**
   * Необходимое количество поддержавших
   * @property
   * @name Approval#numberOfApproversRequired
   * @type {Number}
   */
  numberOfApproversRequired: number;

  /**
   * Количество согласующих
   * @property
   * @name Approval#numberOfApprovers
   * @type {Number}
   */
  numberOfApprovers: number;

  /**
   * Количество одобривших
   * @property
   * @name Approval#numberOfApproversApproved
   * @type {Number}
   */
  numberOfApproversApproved: number;

  /**
   * Группа согласования
   * @property
   * @name Approval#workgroup
   * @type {Workgroup}
   */
  workgroup: Workgroup;

  /**
   * Статус
   * @property
   * @name Approval#status
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Крайний срок
   * @property
   * @name Approval#deadline
   * @type {Date}
   */
  deadline: Date;

  /**
   * Идентификатор сущности,
   * которой принадлежит согласование
   * @property
   * @name Approval#ownerEntityType
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
   * @name Approval#initiator
   * @type {Person}
   */
  initiator: Person;
}
