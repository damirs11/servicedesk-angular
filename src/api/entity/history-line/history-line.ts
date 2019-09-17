import { Entity } from "../entity/entity";
import { User } from "../user/user";

/**
 */
export class HistoryLine extends Entity {
  /**
   * Описание записи
   * @property
   * @type {string}
   */
  subject: string;
  /**
   * Дата
   * @type {Date}
   */
  date: Date;
  /**
   * Пользователь, создавший запись
   * @type {User}
   */
  account: User;
  /**
   * Значение записи. Если это запись чата, value - само сообщение.
   * Если запись в истории, value - новое значение поля сущности
   * @type {String}
   */
  value: string;
  /**
   * Тип записи
   * @type {Number}
   */
  type: number;

  isOwner: any;

  get isChat() {
    return this.type != null;
  }
}
