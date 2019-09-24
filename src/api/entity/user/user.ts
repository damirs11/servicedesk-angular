import { Entity } from '../entity/entity';

/** Пользователь */
export class User extends Entity {
  /** Логин */
  public login: string;

  /** Роли */
  public roles: object[];

  /** Персона */
  public person: object;

}
