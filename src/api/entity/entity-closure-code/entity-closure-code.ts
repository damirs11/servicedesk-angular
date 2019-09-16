import { Entity } from "../entity/entity";
import { EntityTypes } from 'src/api/util/entity-types';

/**
 * Код завершения
 * @class
 * @name EntityClosureCode
 * @extends RESTEntity
 */
export class EntityClosureCode extends Entity {
  entityType: EntityTypes;
}
