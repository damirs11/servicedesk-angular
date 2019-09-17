import { Entity } from "../entity/entity";
import { EntityTypes } from 'src/api/util/entity-types';

/**
 * Классификация
 * @class
 * @name EntityClassification
 * @extends Entity
 */
export class EntityClassification extends Entity {
  entityType: EntityTypes;
}
