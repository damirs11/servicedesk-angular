import { Entity } from '../entity/entity';

/** Приоритет */
export class Workgroup extends Entity {
  /**  */
  public searchcode: string;

  /** Статус */
  public status: string;

  /** Отвественный группы */
  groupManager;

}
