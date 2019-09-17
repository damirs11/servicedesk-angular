import { Entity } from '../entity/entity';
import { Person } from '../person/person';

/**
 * Организация
 */
export class Organization extends Entity {
  /**
   * Почта
   * @property
   * @type {string|null}
   */
  email: string | null;

  /**
   * Папка
   * @type {Person}
   */
  folder: Person;

}
