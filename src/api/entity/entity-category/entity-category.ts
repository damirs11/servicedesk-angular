import { Entity } from "../entity/entity";
import { EntityTypes } from 'src/api/util/entity-types';

/** Классификация */
export class EntityCategory extends Entity {
  public entityType: EntityTypes;
}
