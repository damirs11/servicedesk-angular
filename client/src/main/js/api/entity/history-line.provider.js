import {Parse} from "./decorator/parse.decorator";
import {Instantiate, Nullable} from "./decorator/parse-utils";

HistoryLineProvider.$inject = ["Entity","SD"];
function HistoryLineProvider(Entity,SD) {
    /**
     * Запись в истории
     * Не имеет своих методов работы с REST они внедряются в сущности с помощью миксина
     * @see ENTITY_MIXIN.Historyable
     * @class
     * @name SD.HistoryLine
     * @extends SD.Entity
     */
    return class HistoryLine extends Entity {

        /**
         * Описание записи
         * @property
         * @name SD.HistoryLine#subject
         * @type {string}
         */
        @Parse( String ) subject;
        /**
         * Дата
         * @property
         * @name SD.HistoryLine#date
         * @type {Date}
         */
        @Parse( Instantiate(Date) ) date;
        /**
         * Пользователь, создавший запись
         * @property
         * @name SD.HistoryLine#account
         * @type {SD.User}
         */
        @Parse( data => SD.User.parse(data) ) account;
        /**
         * Значение записи. Если это запись чата, value - само сообщение.
         * Если запись в истории, value - новое значение поля сущности
         * @property
         * @name SD.HistoryLine#value
         * @type {String}
         */
        @Parse( Nullable(String) ) value;
        /**
         * Тип записи
         * @property
         * @name SD.HistoryLine#type
         * @type {Number}
         */
        @Parse( Nullable(String) ) type;

        @Parse(Boolean) isOwner;

        get isChat(){
            return this.type != null;
        }
    }
}

export {HistoryLineProvider};