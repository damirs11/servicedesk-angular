import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Instantiate, Nullable} from "./decorator/parse-utils";
import {serializeId} from "./decorator/serialize-utils";
import {Mixin} from "./mixin/mixin.decorator";
import {TYPEID_WORKORDER} from "./util/entity-type-list";


WorkorderProvider.$inject = ["EditableEntity", "SD", "Historyable", "Accessible"];
function WorkorderProvider(EditableEntity, SD, Historyable, Accessible) {
    /**
     * Персона
     * @class
     * @name SD.Workorder
     * @mixes ENTITY_MIXIN.Historyable
     * @mixes ENTITY_MIXIN.Accessible
     * @extends SD.EditableEntity
     */
    @Mixin(Historyable, Accessible)
    class Workorder extends EditableEntity {
        static $entityTypeId = TYPEID_WORKORDER;
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
        @Serialize(Nullable(Number)) @Parse(Nullable(Number)) labor;

        /**
         * Решение
         * @property
         * @name SD.Workorder#solution
         * @type {String}
         */
        @Serialize(Nullable(String)) @Parse(Nullable(String)) solution;

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
         * Папка
         * @property
         * @name SD.Workorder#folder
         * @type {SD.Folder}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.Folder.parse(data)) folder;

        /**
         * Инициатор
         * @property
         * @name SD.Workorder#initiator
         * @type {SD.Person}
         */
        @Serialize(serializeId) @Parse(data => SD.Person.parse(data)) initiator;

        /**
         * Объект "Назначено"
         * @property
         * @name SD.Workorder#assignment
         * @type {SD.EntityAssignment}
         */
        @Serialize(serializeId) @Parse(data => SD.EntityAssignment.parse(data)) assignment;

        /**
         * Изменение
         * @property
         * @name SD.Workorder#change
         * @type {SD.Change}
         */
        @Serialize(serializeId) @Parse(data => SD.Change.parse(data)) change;

        toString(){
            return String(this.no);
        }

    }
    return Workorder
}

export {WorkorderProvider};