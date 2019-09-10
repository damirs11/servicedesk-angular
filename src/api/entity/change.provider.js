import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Nullable} from "./decorator/parse-utils";
import {serializeId} from "./decorator/serialize-utils";
import {Mixin} from "./mixin/mixin.decorator";
import {EntityTypes} from "./util/entity-types";

ChangeProvider.$inject = ["EditableEntity", "SD", "Historyable", "Approvable", "AttachmentsHolder","Accessible"];
function ChangeProvider(EditableEntity, SD, Historyable, Approvable, AttachmentsHolder, Accessible) {
    /**
     * Изменение
     * @class
     * @name SD.Change
     * @mixes ENTITY_MIXIN.Historyable
     * @mixes ENTITY_MIXIN.Approvable
     * @mixes ENTITY_MIXIN.AttachmentsHolder
     * @mixes ENTITY_MIXIN.Accessible
     * @extends SD.EditableEntity
     */
    @Mixin(Historyable, Approvable, AttachmentsHolder, Accessible)
    class Change extends EditableEntity {
        static $entityTypeId = EntityTypes.Change;
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
         * Решение
         * @property
         * @name SD.Change#solution
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) solution;

        /**
         * Статус
         * @property
         * @name SD.Change#status
         * @type {SD.EntityStatus}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityStatus.parse(data)) status;

        /**
         * Приоритет
         * @property
         * @name SD.Change#priority
         * @type {SD.EntityPriority}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityPriority.parse(data)) priority;

        /**
         * Категория
         * @property
         * @name SD.Change#category
         * @type {SD.EntityCategory}
         */
        @Serialize(serializeId) @Parse(data => SD.EntityCategory.parse(data)) category;

        /**
         * Классификация
         * @property
         * @name SD.Change#classification
         * @type {SD.EntityClassification}
         */
        @Serialize(serializeId) @Parse(data => SD.EntityClassification.parse(data)) classification;

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
         * Реально начато
         * @property
         * @name SD.Change#actualStart
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) actualStart;

        /**
         * Дата фактического выполнения
         * @property
         * @name SD.Change#resolvedDate
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) resolvedDate;

        /**
         * Дата закрытия
         * @property
         * @name SD.Change#closureDate
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) closureDate;

        /**
         * План начала
         * @property
         * @name SD.Change#planStart
         * @type {Date}
         */
        @Serialize(Nullable(Number)) @Parse( Nullable(Date,"new") ) planStart;

        /**
         * План окночания
         * @property
         * @name SD.Change#planFinish
         * @type {Date}
         */
        @Serialize(Nullable(Number)) @Parse( Nullable(Date,"new") ) planFinish;

        /**
         * План продолжительно
         * @property
         * @name SD.Change#planDuration
         * @type {Date}
         */
        @Serialize(Nullable(Number)) @Parse( Nullable(Date,"new") ) planDuration;

        /**
         * Инициатор
         * @property
         * @name SD.Change#person
         * @type {SD.Person}
         */
        @Serialize(serializeId) @Parse(data => SD.Person.parse(data)) initiator;

        /**
         * Менеджер
         * @property
         * @name SD.Change#manager
         * @type {SD.Person}
         */
        @Serialize(serializeId) @Parse(data => SD.Person.parse(data)) manager;

        /**
         * Объект обслуживания
         * @property
         * @name SD.Change#configurationItem
         * @type {SD.ConfigurationItem}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.ConfigurationItem.parse(data)) configurationItem;

        /**
         * Код завершения
         * @property
         * @name SD.Change#closureCode
         * @type {SD.EntityClosureCode}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.EntityClosureCode.parse(data)) closureCode;

        /**
         * Сущность "назначено"
         * @property
         * @name SD.Change#assignment
         * @type {SD.EntityAssignment}
         */
        @Serialize((ag,name,mode) => mode == "FULL" ? ag.$serialize() : ag.$modifiedData)
        @Parse(data => SD.EntityAssignment.parse(data)) assignment;

        /**
         * Кастомный код 1(Система)
         * @property
         * @name SD.Change#entityCode1
         * @type {SD.EntityCode1}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.EntityCode1.parse(data)) system;

        /**
         * Папка
         * @property
         * @name SD.Change#folder
         * @type {SD.Folder}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.Folder.parse(data)) folder;

        toString(){
            return String(this.no);
        }

    }
    return Change;
}

export {ChangeProvider};