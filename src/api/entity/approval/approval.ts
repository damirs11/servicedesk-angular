import { Entity } from "../entity/entity";
import { Workgroup } from "../workgroup/workgroup";
import { EntityStatus } from "../entity-status/entity-status";
import { EntityTypes } from "src/api/util/entity-types";
import { Person } from '../person/person';

/**
 * Голосование
  */
export class Approval extends Entity {
  static readonly entityTypeId: EntityTypes = EntityTypes.Approval;
  /**
   * ID сущности
   */
  subject: string;

  /**
   * Необходимое количество поддержавших
   */
  numberOfApproversRequired: number;

  /**
   * Количество согласующих
   */
  numberOfApprovers: number;

  /**
   * Количество одобривших
   */
  numberOfApproversApproved: number;

  /**
   * Группа согласования
   */
  workgroup: Workgroup;

  /**
   * Статус
   */
  status: EntityStatus;

  /**
   * Крайний срок
   */
  deadline: Date;

  /**
   * Идентификатор сущности,
   * которой принадлежит согласование
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
   */
  initiator: Person;
}
