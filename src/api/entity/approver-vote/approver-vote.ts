import { Entity } from "../entity/entity";
import { Person } from "../person/person";

/**
 * Голосование
 */
export class ApproverVote extends Entity {
  /**
   * ID сущности, к которой привязаны голосо
   */

  entityId: number;

  /**
   * Согласовано
   */

  approved: number;

  /**
   * Согласующий
   */
  approver: Person;

  /**
   * Причина
   */
  reason: string;

  /**
   * Тип сущности
   */

  entityType: string;
}
