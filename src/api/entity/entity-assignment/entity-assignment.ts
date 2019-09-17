import { Entity } from "../entity/entity";
import { EntityPriority } from "../entity-priority/entity-priority";
import { EntityStatus } from "../entity-status/entity-status";
import { Workgroup } from "../workgroup/workgroup";
import { Person } from '../person/person';

export class EntityAssignment extends Entity {
  /**
   * Исполнитель
   * @property
   * @type {Person}
   */
  executor: Person;

  /**
   * Приоритет
   * @type {EntityPriority}
   */
  priority: EntityPriority;

  /**
   * Статус
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Рабочая группа
   * @type {Workgroup}
   */
  workgroup: Workgroup;
}
