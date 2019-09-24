import { Entity } from "../entity/entity";
import { EntityTypes } from 'src/api/util/entity-types';

/** База известных ошибок */
export class FAQ extends Entity {
  public entityType: EntityTypes;
}
