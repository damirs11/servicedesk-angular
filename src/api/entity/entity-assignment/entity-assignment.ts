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
   * @property
   * @type {EntityPriority}
   */
  priority: EntityPriority;

  /**
   * Статус
   * @property
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Рабочая группа
   * @property
   * @type {Workgroup}
   */
  workgroup: Workgroup;
}
