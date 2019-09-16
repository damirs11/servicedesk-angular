import { Entity } from "../entity/entity";
import { EntityTypes } from 'src/api/util/entity-types';

/**
 * База известных ошибок
 * @class
 * @name FAQ
 * @extends RESTEntity
 */
export class FAQ extends Entity {
  entityType: EntityTypes;
}
