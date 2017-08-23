import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Nullable} from "./decorator/parse-utils";


ChangeProvider.$inject = ["EditableEntity", "SD"];
function ChangeProvider(EditableEntity, SD) {
    /**
     * Персона
     * @class
     * @name SD.Change
     * @extends SD.EditableEntity
     */
    return class Change extends EditableEntity {
        /**
         * Номер
         * @property
         * @name SD.Change#no
         * @type {number}
         */
        @Serialize(Number) @Parse(Number) no;

        /**
         * Тема
         * @property
         * @name SD.Change#subject
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) subject;

        /**
         * Описание
         * @property
         * @name SD.Change#description
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) description;

        /**
         * Статус
         * @property
         * @name SD.Change#status
         * @type {SD.EntityStatus}
         */
        @Serialize(status => status.id) @Parse(data => SD.EntityStatus.parse(data)) status;

        /**
         * Приоритет
         * @property
         * @name SD.Change#priority
         * @type {SD.EntityPriority}
         */
        @Serialize(pr => pr.id) @Parse(data => SD.EntityPriority.parse(data)) priority;

        /**
         * Дата создания
         * @property
         * @name SD.Change#createdDate
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) createdDate;

        /**
         * Крайний срок
         * @property
         * @name SD.Change#deadline
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) deadline;

        /**
         * Дата фактического выполнения
         * @property
         * @name SD.Change#resolvedDate
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) resolvedDate;

        /**
         * Инициатор
         * @property
         * @name SD.Change#person
         * @type {SD.Person}
         */
        @Serialize(p => p.id) @Parse(data => SD.Person.parse(data)) initiator;

        /**
         * Менеджер
         * @property
         * @name SD.Change#manager
         * @type {SD.Person}
         */
        @Serialize(p => p.id) @Parse(data => SD.Person.parse(data)) manager;

        toString(){
            return this.no;
        }

    };
}

export {ChangeProvider};