import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Nullable} from "./decorator/parse-utils";
import {serializeId} from "./decorator/serialize-utils";
import {Mixin} from "./mixin/mixin.decorator";


ChangeProvider.$inject = ["EditableEntity", "SD", "Historyable", "Approvable", "AttachmentsHolder","Accessible"];
function ChangeProvider(EditableEntity, SD, Historyable, Approvable, AttachmentsHolder, Accessible) {
    /**
     * Персона
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
        static $entityTypeId = 724041768;
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
        @Serialize(serializeId) @Parse(data => SD.Person.parse(data)) initiator;

        /**
         * Менеджер
         * @property
         * @name SD.Change#manager
         * @type {SD.Person}
         */
        @Serialize(serializeId) @Parse(data => SD.Person.parse(data)) manager;

        /**
         * Классификация
         * @property
         * @name SD.Change#classification
         * @type {SD.EntityClassification}
         */
        @Serialize(serializeId) @Parse(data => SD.EntityClassification.parse(data)) classification;

        /**
         * Категория
         * @property
         * @name SD.Change#category
         * @type {SD.EntityCategory}
         */
        @Serialize(serializeId) @Parse(data => SD.EntityCategory.parse(data)) category;

        /**
         * Объект обслуживания
         * @property
         * @name SD.Change#configurationItem
         * @type {SD.ConfigurationItem}
         */
        @Serialize(serializeId) @Parse(data => SD.ConfigurationItem.parse(data)) configurationItem;

        /**
         * Сущность "назначено"
         * @property
         * @name SD.Change#assignment
         * @type {SD.EntityAssignment}
         */
        @Serialize("assignment",(ag) => ag.$serialize())
        @Parse("assignment", data => SD.EntityAssignment.parse(data)) assignment;

        toString(){
            return this.no;
        }

    }
    return Change
}

export {ChangeProvider};