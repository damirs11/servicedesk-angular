import { Entity } from '../entity/entity';

/**
 * Пользователь
 */
export class User extends Entity {
  /**
   * Логин
   */
  login: string;

  /**
   * Роли
   */
  roles: [Object];

  /**
   * Персона
   */
  person: object;

}
