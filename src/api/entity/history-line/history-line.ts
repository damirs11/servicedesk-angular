import { Entity } from "../entity/entity";
import { User } from "../user/user";

/**
 * Запись в истории
 */
export class HistoryLine extends Entity {
  /** Описание записи */
  public subject: string;
  /** Дата */
  public date: Date;
  /** Пользователь, создавший запись */
  public account: User;
  /**
   * Значение записи. Если это запись чата, value - само сообщение.
   * Если запись в истории, value - новое значение поля сущности
   */
  public value: string;
  /** Тип записи */
  public type: number;

  public isOwner: any;

  get isChat() {
    return this.type != null;
  }
}
