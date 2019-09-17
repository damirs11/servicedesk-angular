import { Entity } from '../entity/entity';
import { EntityTypes } from 'src/api/util/entity-types';

/**
 * Папка
 * @class
 * @name Folder
 * @extends Entity
 */
export class Folder extends Entity {
  entityType: EntityTypes;
}
