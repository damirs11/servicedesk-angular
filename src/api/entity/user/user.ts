import { Entity } from '../entity/entity';

/**
 * Пользователь
 * @class
 * @extends Entity
 * @name User
 */
export class User extends Entity {
  /**
   * Логин
   * @property
   * @name User#login
   * @type {string}
   */
  login: string;

  /**
   * Роли
   * @property
   * @name User#roles
   * @type {[Object]}
   */
  roles: [Object];

  /**
   * Персона
   * @property
   * @name person
   * @type {object}
   */
  person: object;

}
