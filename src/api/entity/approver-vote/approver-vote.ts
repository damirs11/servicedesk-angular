import { Entity } from "../entity/entity";
import { Person } from "../person/person";

/** Голосование */
export class ApproverVote extends Entity {
  /** ID сущности, к которой привязаны голосования */
  public entityId: number;

  /** Согласовано */
  public approved: number;

  /** Согласующий */
  public approver: Person;

  /** Причина */
  public reason: string;

  /** Тип сущности */
  public entityType: string;
}
