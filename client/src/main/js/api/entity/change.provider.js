import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";


ChangeProvider.$inject = ["EditableEntity", "SD","$connector"];
function ChangeProvider(EditableEntity, SD, $connector) {
    /**
     * Персона
     * @class
     * @extends SD.EditableEntity
     * @name SD.Change
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
        @Serialize(String) @Parse(String) subject;

        /**
         * Описание
         * @property
         * @name SD.Change#description
         * @type {string}
         */
        @Serialize(String) @Parse(String) description;

        /**
         * Статус
         * @property
         * @name SD.Change#status
         * @type {SD.Status}
         */
        @Serialize(status => status.id) @Parse(data => SD.Status.parse(data)) status;

        /**
         * Приоритет
         * @property
         * @name SD.Change#priority
         * @type {string}
         */
        @Serialize(pr => pr.id) @Parse(data => SD.Priority.parse(data)) priority;

        /**
         * Дата создания
         * @property
         * @name SD.Change#createdDate
         * @type {Date}
         */
        @Serialize(Number) @Parse(Date) createdDate;

        /**
         * Крайний срок
         * @property
         * @name SD.Change#deadline
         * @type {Date}
         */
        @Serialize(Number) @Parse(Date) deadline;

        /**
         * Дата фактического выполнения
         * @property
         * @name SD.Change#resolvedDate
         * @type {Date}
         */
        @Serialize(Number) @Parse(Date) resolvedDate;

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

    };
}

export {ChangeProvider};