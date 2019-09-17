import { Entity } from "../entity/entity";
import { EntityTypes } from 'src/api/util/entity-types';

/**
 * Классификация
 * @class
 * @name EntityCategory
 * @extends Entity
 */
export class EntityCategory extends Entity {
  entityType: EntityTypes;
}
