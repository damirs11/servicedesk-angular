import { Entity } from '../entity/entity';

/**
 * Организация
 * @class
 * @name Organization
 */
export class Organization extends Entity {
  /**
   * Название
   * @property
   * @name Organization#name
   * @type {string}
   */
  name: string;

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
  folder;

  toString() {
    return this.name;
  }
}
