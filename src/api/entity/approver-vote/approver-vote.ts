import { Entity } from "../entity/entity";
import { Person } from "../person/person";

/**
 * Голосование
 */
export class ApproverVote extends Entity {
  /**
   * ID сущности, к которой привязаны голосо
   * @property
   * @type {Number}
   */

  entityId: number;

  /**
   * Согласовано
   * @type {Number}
   */

  approved: number;

  /**
   * Согласующий
   * @type {Person}
   */
  approver: Person;

  /**
   * Причина
   * @type {String}
   */
  reason: string;

  /**
   * Тип сущности
   * @type {String}
   */

  entityType: string;
}
