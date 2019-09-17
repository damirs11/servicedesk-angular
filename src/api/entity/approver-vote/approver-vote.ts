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
   * @property
   * @type {Number}
   */

  approved: number;

  /**
   * Согласующий
   * @property
   * @type {Person}
   */
  approver: Person;

  /**
   * Причина
   * @property
   * @type {String}
   */
  reason: string;

  /**
   * Тип сущности
   * @property
   * @type {String}
   */

  entityType: string;
}
