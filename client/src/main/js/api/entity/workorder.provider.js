import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Instantiate, Nullable} from "./decorator/parse-utils";
import {serializeId} from "./decorator/serialize-utils";


WorkorderProvider.$inject = ["EditableEntity", "SD"];
function WorkorderProvider(EditableEntity, SD) {
    /**
     * Персона
     * @class
     * @name SD.Workorder
     * @extends SD.EditableEntity
     */
    return class Workorder extends EditableEntity {
        /**
         * Номер
         * @property
         * @name SD.Workorder#no
         * @type {number}
         */
        @Serialize(Number) @Parse(Number) no;

        /**
         * Тема
         * @property
         * @name SD.Workorder#subject
         * @type {String}
         */
        @Serialize(String) @Parse(String) subject;

        /**
         * Подробная информация
         * @property
         * @name SD.Workorder#description
         * @type {String}
         */
        @Serialize(String) @Parse(String) description;

        /**
         * Трудозатраты
         * @property
         * @name SD.Workorder#labor
         * @type {Number}
         */
        @Serialize(Number) @Parse(Number) labor;

        /**
         * Решение
         * @property
         * @name SD.Workorder#solution
         * @type {String}
         */
        @Serialize(String) @Parse(String) solution;

        /**
         * Статус
         * @property
         * @name SD.Workorder#status
         * @type {SD.EntityStatus}
         */
        @Serialize(serializeId) @Parse(data => SD.EntityStatus.parse(data)) status;

        /**
         * Категория
         * @property
         * @name SD.Workorder#category
         * @type {SD.EntityCategory}
         */
        @Serialize(serializeId) @Parse(data => SD.EntityCategory.parse(data)) category;

        /**
         * Код завершения
         * @property
         * @name SD.Workorder#closureCode
         * @type {SD.EntityClosureCode}
         */
        @Serialize(serializeId) @Parse(data => SD.EntityClosureCode.parse(data)) closureCode;

        /**
         * Дата создания
         * @property
         * @name SD.Workorder#createdDate
         * @type {Date}
         */
        @Serialize(Number) @Parse(Instantiate(Date)) createdDate;

        /**
         * Крайний срок
         * @property
         * @name SD.Workorder#deadline
         * @type {Date}
         */
        @Serialize(Number) @Parse(Nullable(Date,"new")) deadline;

        /**
         * Фактически выполнено
         * @property
         * @name SD.Workorder#resolvedDate
         * @type {Date}
         */
        @Serialize(Number) @Parse(Nullable(Date,"new")) resolvedDate;

        /**
         * Дата изменения
         * @property
         * @name SD.Workorder#modifyDate
         * @type {Date}
         */
        @Serialize(Number) @Parse(Nullable(Date,"new")) modifyDate;

        /**
         * Наряд просрочен
         * @property
         * @name SD.Workorder#expired
         * @type {Boolean}
         */
        @Serialize(Boolean) @Parse(Nullable(Boolean)) expired;

        /**
         * Инициатор
         * @property
         * @name SD.Workorder#initiator
         * @type {SD.Person}
         */
        @Serialize(serializeId) @Parse(data => SD.Person.parse(data)) initiator;

        /**
         * Исполнитель
         * @property
         * @name SD.Workorder#assigneePerson
         * @type {SD.Person}
         */
        @Serialize(serializeId) @Parse(data => SD.Person.parse(data)) assigneePerson;

        /**
         * Изменение
         * @property
         * @name SD.Workorder#change
         * @type {SD.Change}
         */
        @Serialize(serializeId) @Parse(data => SD.Change.parse(data)) change;

        toString(){
            return this.no;
        }

    };
}

export {WorkorderProvider};