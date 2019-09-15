import { Entity } from "../entity/entity";
import { EntityPriority } from "../entity-priority/entity-priority";
import { EntityStatus } from "../entity-status/entity-status";
import { Workgroup } from "../workgroup/workgroup";
import { Person } from '../person/person';

export class EntityAssignment extends Entity {
  /**
   * Исполнитель
   * @property
   * @name EntityAssignment#executor
   * @type {Person}
   */
  executor: Person;

  /**
   * Приоритет
   * @property
   * @name EntityAssignment#priority
   * @type {EntityPriority}
   */
  priority: EntityPriority;

  /**
   * Статус
   * @property
   * @name EntityAssignment#status
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Рабочая группа
   * @property
   * @name EntityAssignment#workgroup
   * @type {Workgroup}
   */
  workgroup: Workgroup;
}
