import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Nullable} from "./decorator/parse-utils";
import {serializeId} from "./decorator/serialize-utils";
import {Mixin} from "./mixin/mixin.decorator";
import {EntityTypes} from "./util/entity-types";

ServiceCallProvider.$inject = ["EditableEntity", "SD", "Historyable", "Accessible", "AttachmentsHolder"];
function ServiceCallProvider(EditableEntity, SD, Historyable, Accessible, AttachmentsHolder) {
    /**
     * Заявка
     * @class
     * @name SD.ServiceCall
     * @mixes ENTITY_MIXIN.Historyable
     * @mixes ENTITY_MIXIN.Accessible
     * * @mixes ENTITY_MIXIN.AttachmentsHolder
     * @extends SD.EditableEntity
     */
    @Mixin(Historyable, Accessible, AttachmentsHolder)
    class ServiceCall extends EditableEntity {
        static $entityTypeId = EntityTypes.ServiceCall;
        /**
         * Номер
         * @property
         * @name SD.ServiceCall#no
         * @type {number}
         */
        @Serialize(Number) @Parse(Number) no;

        /**
         * Тема
         * @property
         * @name SD.ServiceCall#subject
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
         * @name SD.ServiceCall#solution
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) solution;

        /**
         * Статус
         * @property
         * @name SD.ServiceCall#status
         * @type {SD.EntityStatus}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityStatus.parse(data)) status;

        /**
         * Приоритет
         * @property
         * @name SD.ServiceCall#priority
         * @type {SD.EntityPriority}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityPriority.parse(data)) priority;

        /**
         * Категория
         * @property
         * @name SD.ServiceCall#category
         * @type {SD.EntityCategory}
         */
        @Serialize(serializeId) @Parse(data => SD.EntityCategory.parse(data)) category;

        /**
         * Классификация
         * @property
         * @name SD.ServiceCall#classification
         * @type {SD.EntityClassification}
         */
        @Serialize(serializeId) @Parse(data => SD.EntityClassification.parse(data)) classification;

        /**
         * Крайний срок
         * @property
         * @name SD.ServiceCall#deadline
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) deadline;

        /**
         * Дата фактического выполнения
         * @property
         * @name SD.ServiceCall#resolvedDate
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) resolvedDate;

        /**
         * Дата закрытия
         * @property
         * @name SD.ServiceCall#closureDate
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) closureDate;

        /**
         * Инициатор
         * @property
         * @name SD.ServiceCall#person
         * @type {SD.Person}
         */
        @Serialize(serializeId) @Parse(data => SD.Person.parse(data)) initiator;

        /**
         * Объект обслуживания
         * @property
         * @name SD.ServiceCall#configurationItem
         * @type {SD.ConfigurationItem}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.ConfigurationItem.parse(data)) configurationItem;

        /**
         * Код завершения
         * @property
         * @name SD.ServiceCall#closureCode
         * @type {SD.EntityClosureCode}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.EntityClosureCode.parse(data)) closureCode;

        /**
         * Папка
         * @property
         * @name SD.Change#folder
         * @type {SD.Folder}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.Folder.parse(data)) folder;
        
        /**
         * Сущность "назначено"
         * @property
         * @name SD.ServiceCall#assignment
         * @type {SD.EntityAssignment}
         */
        @Serialize((ag,name,mode) => mode === "FULL" ? ag.$serialize() : ag.$modifiedData)
        @Parse(data => SD.EntityAssignment.parse(data)) assignment;

        toString(){
            return String(this.no);
        }

    }
    return ServiceCall;
}

export {ServiceCallProvider};