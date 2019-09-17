import { Entity } from '../entity/entity';

/**
 * Пользователь
 */
export class User extends Entity {
  /**
   * Логин
   * @property
   * @type {string}
   */
  login: string;

  /**
   * Роли
   * @type {[Object]}
   */
  roles: [Object];

  /**
   * Персона
   * @type {object}
   */
  person: object;

}
