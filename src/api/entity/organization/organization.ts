import { Entity } from '../entity/entity';
import { Person } from '../person/person';

/**
 * Организация
 * @class
 * @name Organization
 */
export class Organization extends Entity {
  /**
   * Почта
   * @property
   * @name Organization#email
   * @type {string|null}
   */
  email: string | null;

  /**
   * Папка
   * @property
   * @name Organization#folder
   * @type {Person}
   */
  folder: Person;

}
