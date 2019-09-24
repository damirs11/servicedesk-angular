import { Entity } from '../entity/entity';
import { EntityTypes } from 'src/api/util/entity-types';

/** Папка */
export class Folder extends Entity {
  public entityType: EntityTypes;
}
