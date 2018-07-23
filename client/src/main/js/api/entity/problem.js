import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Nullable} from "./decorator/parse-utils";
import {serializeId} from "./decorator/serialize-utils";
import {Mixin} from "./mixin/mixin.decorator";
import {TYPEID_PROBLEM} from "./util/entity-type-list";


ProblemProvider.$inject = ["EditableEntity", "SD", "Historyable", "Approvable", "AttachmentsHolder","Accessible"];
function ProblemProvider(EditableEntity, SD, Historyable, Approvable, AttachmentsHolder, Accessible) {
    /**
     * Персона
     * @class
     * @name SD.Problem
     * @mixes ENTITY_MIXIN.Historyable
     * @mixes ENTITY_MIXIN.Approvable
     * @mixes ENTITY_MIXIN.AttachmentsHolder
     * @mixes ENTITY_MIXIN.Accessible
     * @extends SD.EditableEntity
     */
    @Mixin(Historyable, Approvable, AttachmentsHolder, Accessible)
    class Problem extends EditableEntity {
        static $entityTypeId = TYPEID_PROBLEM;
        /**
         * Номер
         * @property
         * @name SD.Problem#no
         * @type {number}
         */
        @Serialize(Number) @Parse(Number) no;

        /**
         * Статус
         * @property
         * @name SD.Problem#status
         * @type {SD.EntityStatus}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityStatus.parse(data)) status;

        /**
         * Инициатор
         * @property
         * @name SD.Problem#initiator
         * @type {SD.Person}
         */
        @Serialize(serializeId) @Parse(data => SD.Person.parse(data)) initiator;

        /**
         * Объект обслуживания
         * @property
         * @name SD.Problem#configurationItem
         * @type {SD.ConfigurationItem}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.ConfigurationItem.parse(data)) configurationItem;

        /**
         * Тема
         * @property
         * @name SD.Problem#subject
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) subject;

        /**
         * Описание
         * @property
         * @name SD.Problem#description
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) description;

        /**
         * Ссылки на логи
         * @property
         * @name SD.Problem#logLinks
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) logLinks;

        /**
         * Ссылка на пробелму в jira
         * @property
         * @name SD.Problem#jiraLink
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) jiraLink;
        /**
         * @property
         * @name SD.Problem#toVendor
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) toVendor;
        /**
         * Обходное решение
         * @property
         * @name SD.Problem#workaround
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) workaround;

        /**
         * Решение
         * @property
         * @name SD.Problem#solution
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) solution;

        /**
         * Приоритет
         * @property
         * @name SD.Problem#priority
         * @type {SD.EntityPriority}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityPriority.parse(data)) priority;

        /**
         * Крайний срок
         * @property
         * @name SD.Problem#deadline
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) deadline;

        /**
         * Дата фактического выполнения
         * @property
         * @name SD.Problem#resolvedDate
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) resolvedDate;

        /**
         * Дата закрытия
         * @property
         * @name SD.Problem#closureDate
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) closureDate;

        /**
         * Проблема просрочена
         * @property
         * @name SD.Problem#isOverdue
         * @type {Date}
         */
        @Serialize(Boolean) @Parse( Nullable(Boolean) ) isOverdue;

        /**
         * Персона, что просрочила проблему
         * @property
         * @name SD.Problem#whoOverdue
         * @type {SD.Person}
         */
        @Serialize(serializeId) @Parse(data => SD.Person.parse(data)) whoOverdue;

        /**
         * План окночания
         * @property
         * @name SD.Problem#planFinish
         * @type {Date}
         */
        @Serialize(Nullable(Number)) @Parse( Nullable(Date,"new") ) planFinish;

        /**
         * Причина отсрочки
         * @property
         * @name SD.Problem#deferralReason
         * @type {string}
         */
        @Serialize(String) @Parse(Nullable(String)) deferralReason;

        /**
         * Сущность "назначено"
         * @property
         * @name SD.Problem#assignment
         * @type {SD.EntityAssignment}
         */
        @Serialize((ag,name,mode) => mode == "FULL" ? ag.$serialize() : ag.$modifiedData)
        @Parse(data => SD.EntityAssignment.parse(data)) assignment;

        /**
         * Категория
         * @property
         * @name SD.Problem#category
         * @type {SD.EntityCategory}
         */
        @Serialize(serializeId) @Parse(data => SD.EntityCategory.parse(data)) category;

        /**
         * Классификация
         * @property
         * @name SD.Problem#classification
         * @type {SD.EntityClassification}
         */
        @Serialize(serializeId) @Parse(data => SD.EntityClassification.parse(data)) classification;

        /**
         * Код завершения
         * @property
         * @name SD.Problem#closureCode
         * @type {SD.EntityClosureCode}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.EntityClosureCode.parse(data)) closureCode;

        /**
         * Папка
         * @property
         * @name SD.Problem#folder
         * @type {SD.Folder}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.Folder.parse(data)) folder;

        /**
         * Не включать в отчет заказчику
         * @property
         * @name SD.Problem#isOverdue
         * @type {Date}
         */
        @Serialize(Boolean) @Parse( Nullable(Boolean) ) notAttachInReport;

        /**
         *
         * @property
         * @name SD.Problem#versionDate
         * @type {Date}
         */
        @Serialize(Number) @Parse( Nullable(Date,"new") ) versionDate;

        toString(){
            return String(this.no);
        }

    }
    return Problem
}

export {ProblemProvider};