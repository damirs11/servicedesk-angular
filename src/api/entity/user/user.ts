import { Entity } from '../entity/entity';

/**
 * Пользователь
 * @class
 * @extends RESTEntity
 * @name User
 */
export class User extends Entity {
  /**
   * Имя пользователя
   * @property
   * @name User#name
   * @type {string}
   */
  name: string;

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
  person;

  toString() {
    return this.name;
  }
}
