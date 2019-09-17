import { Entity } from "../entity/entity";
import { EntityTypes } from 'src/api/util/entity-types';

/**
 * База известных ошибок
 * @class
 * @name FAQ
 * @extends Entity
 */
export class FAQ extends Entity {
  entityType: EntityTypes;
}
