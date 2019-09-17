import { Entity } from "../entity/entity";
import { User } from "../user/user";

/**
 */
export class HistoryLine extends Entity {
  /**
   * Описание записи
   */
  subject: string;
  /**
   * Дата
   */
  date: Date;
  /**
   * Пользователь, создавший запись
   */
  account: User;
  /**
   * Значение записи. Если это запись чата, value - само сообщение.
   * Если запись в истории, value - новое значение поля сущности
   */
  value: string;
  /**
   * Тип записи
   */
  type: number;

  isOwner: any;

  get isChat() {
    return this.type != null;
  }
}
