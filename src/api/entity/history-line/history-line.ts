import { Entity } from '../entity/entity';
import { User } from '../user/user';

/**
 * Запись в истории
 * Не имеет своих методов работы с REST они внедряются в сущности с помощью миксина
 * @see ENTITY_MIXIN.Historyable
 * @class
 * @name HistoryLine
 * @extends Entity
 */
export class HistoryLine extends Entity {
  /**
   * Описание записи
   * @property
   * @name HistoryLine#subject
   * @type {string}
   */
  subject: string;
  /**
   * Дата
   * @property
   * @name HistoryLine#date
   * @type {Date}
   */
  date: Date;
  /**
   * Пользователь, создавший запись
   * @property
   * @name HistoryLine#account
   * @type {User}
   */
  account: User;
  /**
   * Значение записи. Если это запись чата, value - само сообщение.
   * Если запись в истории, value - новое значение поля сущности
   * @property
   * @name HistoryLine#value
   * @type {String}
   */
  value: string;
  /**
   * Тип записи
   * @property
   * @name HistoryLine#type
   * @type {Number}
   */
  type: number;

  isOwner: any;

  get isChat() {
    return this.type != null;
  }
}
