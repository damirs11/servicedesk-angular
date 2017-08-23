import {Parse} from "./decorator/parse.decorator";
import {Instantiate} from "./decorator/parse-utils";

HistoryLineProvider.$inject = ["Entity","SD"];
function HistoryLineProvider(Entity,SD) {
    /**
     * Запись в истории
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
    }
}

export {HistoryLineProvider};