import {Parse} from "./decorator/parse.decorator";
import {Instantiate} from "./decorator/parse-utils";

HistoryLineProvider.$inject = ["Entity","SD"];
function HistoryLineProvider(Entity,SD) {
    /**
     * Статус
     * @class
     * @name SD.HistoryLine
     * @extends SD.Entity
     */
    return class HistoryLine extends Entity {

        /**
         * Описание события
         * @property
         * @name SD.HistoryLine#description
         * @type {string}
         */
        @Parse( String ) description;
        /**
         * Дата события
         * @property
         * @name SD.HistoryLine#date
         * @type {Date}
         */
        @Parse( Instantiate(Date) ) date;
        /**
         * Пользователь, что внес изменения
         * @property
         * @name SD.HistoryLine#user
         * @type {SD.User}
         */
        @Parse( (data) => SD.User.parse(data) ) user;

        toString(){
            return this.name
        }
    };
}

export {HistoryLineProvider};