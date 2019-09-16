import { Entity } from '../entity/entity';
import { EntityTypes } from 'src/api/util/entity-types';

/**
 * Папка
 * @class
 * @name Folder
 * @extends RESTEntity
 */
export class Folder extends Entity {
  entityType: EntityTypes;
}
