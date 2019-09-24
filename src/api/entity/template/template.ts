import { Entity } from '../entity/entity';
import { Folder } from '../folder/folder';

/** Приоритет */
export class Template extends Entity {
  /** Папка */
  public folder: Folder;
}
