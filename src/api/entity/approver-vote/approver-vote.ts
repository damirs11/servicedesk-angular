import { Entity } from "../entity/entity";
import { Person } from '../person/person';

/**
 * Голосование
 * @class
 * @extends RESTEntity
 * @name ApproverVote
 */
export class ApproverVote extends Entity {
  /**
   * ID сущности, к которой привязаны голосо
   * @property
   * @name ApproverVote#entityId
   * @type {Number}
   */

  entityId: number;

  /**
   * Согласовано
   * @property
   * @name ApproverVote#approved
   * @type {Number}
   */

  approved: number;

  /**
   * Согласующий
   * @property
   * @name ApproverVote#approver
   * @type {Person}
   */
  approver: Person;

  /**
   * Причина
   * @property
   * @name ApproverVote#reason
   * @type {String}
   */
  reason: string;

  /**
   * Тип сущности
   * @property
   * @name ApproverVote#reason
   * @type {String}
   */

  entityType: string;
}
