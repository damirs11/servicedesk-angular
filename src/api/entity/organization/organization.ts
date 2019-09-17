import { Entity } from '../entity/entity';
import { Person } from '../person/person';

/**
 * Организация
 */
export class Organization extends Entity {
  /**
   * Почта
   */
  email: string | null;

  /**
   * Папка
   */
  folder: Person;

}
