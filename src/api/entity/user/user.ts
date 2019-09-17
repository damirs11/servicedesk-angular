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
   * @property
   * @type {[Object]}
   */
  roles: [Object];

  /**
   * Персона
   * @property
   * @type {object}
   */
  person: object;

}
