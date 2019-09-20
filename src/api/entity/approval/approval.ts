import { Entity } from "../entity/entity";
import { Workgroup } from "../workgroup/workgroup";
import { EntityStatus } from "../entity-status/entity-status";
import { EntityTypes } from "src/api/util/entity-types";
import { Person } from '../person/person';

/** Голосование */
export class Approval extends Entity {
  static readonly entityTypeId: EntityTypes = EntityTypes.Approval;
  /** ID сущности */
  public subject: string;

  /** Необходимое количество поддержавших */
  public numberOfApproversRequired: number;

  /** Количество согласующих */
  public numberOfApprovers: number;

  /** Количество одобривших */
  public numberOfApproversApproved: number;

  /** Группа согласования */
  public workgroup: Workgroup;

  /** Статус */
  public status: EntityStatus;

  /** Крайний срок */
  public deadline: Date;

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
  public ownerEntityType: number;

  /** Инициатор согласования */
  public initiator: Person;
}
