import { Entity } from "../entity/entity";
import { EntityTypes } from "src/api/util/entity-types";
/** Классификация */
export class EntityClassification extends Entity {
  public entityType: EntityTypes;
}
