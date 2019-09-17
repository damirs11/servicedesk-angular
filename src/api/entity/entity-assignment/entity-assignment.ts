import { Entity } from "../entity/entity";
import { EntityPriority } from "../entity-priority/entity-priority";
import { EntityStatus } from "../entity-status/entity-status";
import { Workgroup } from "../workgroup/workgroup";
import { Person } from '../person/person';

export class EntityAssignment extends Entity {
  /**
   * Исполнитель
   */
  executor: Person;

  /**
   * Приоритет
   */
  priority: EntityPriority;

  /**
   * Статус
   */
  status: EntityStatus;

  /**
   * Рабочая группа
   */
  workgroup: Workgroup;
}
