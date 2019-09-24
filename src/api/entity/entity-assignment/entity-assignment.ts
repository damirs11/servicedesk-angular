import { Entity } from "../entity/entity";
import { EntityPriority } from "../entity-priority/entity-priority";
import { EntityStatus } from "../entity-status/entity-status";
import { Workgroup } from "../workgroup/workgroup";
import { Person } from '../person/person';

export class EntityAssignment extends Entity {
  /** Исполнитель */
  public executor: Person;

  /** Приоритет */
  public priority: EntityPriority;

  /** Статус */
  public status: EntityStatus;

  /** Рабочая группа */
  public workgroup: Workgroup;
}
