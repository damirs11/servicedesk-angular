import { Entity } from "../entity/entity";
import { EntityTypes } from 'src/api/util/entity-types';

/**
 * Кастомный код 1
 * @class
 * @name EntityCode1
 * @extends RESTEntity
 */
export class EntityCode1 extends Entity {
  entityType: EntityTypes;
}
