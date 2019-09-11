/**
 * Запись в истории
 * Не имеет своих методов работы с REST они внедряются в сущности с помощью миксина
 * @see ENTITY_MIXIN.Historyable
 * @class
 * @name SD.HistoryLine
 * @extends SD.Entity
 */
export class HistoryLine {

    /**
     * Описание записи
     * @property
     * @name SD.HistoryLine#subject
     * @type {string}
     */
    subject: string;
    /**
     * Дата
     * @property
     * @name SD.HistoryLine#date
     * @type {Date}
     */
    date: Date;
    /**
     * Пользователь, создавший запись
     * @property
     * @name SD.HistoryLine#account
     * @type {SD.User}
     */
    account;
    /**
     * Значение записи. Если это запись чата, value - само сообщение.
     * Если запись в истории, value - новое значение поля сущности
     * @property
     * @name SD.HistoryLine#value
     * @type {String}
     */
    value: string;
    /**
     * Тип записи
     * @property
     * @name SD.HistoryLine#type
     * @type {Number}
     */
    type: number;

    isOwner: any;

    get isChat() {
        return this.type != null;
    }
}
