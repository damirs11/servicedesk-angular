import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Instantiate, Nullable} from "./decorator/parse-utils";
import {serializeId} from "./decorator/serialize-utils";
import {Mixin} from "./mixin/mixin.decorator";
import {EntityTypes} from "./util/entity-types";

/**
 * Персона
 * @class
 * @name SD.Workorder
 * @mixes ENTITY_MIXIN.Historyable
 * @mixes ENTITY_MIXIN.Accessible
 * @mixes ENTITY_MIXIN.AttachmentsHolder
 * @extends SD.EditableEntity
 */
@Mixin(Historyable, Accessible, AttachmentsHolder)
export class Workorder {
    static $entityTypeId = EntityTypes.Workorder;
    /**
     * Номер
     * @property
     * @name SD.Workorder#no
     * @type {number}
     */
    @Serialize(Number) @Parse(Number) no: number;

    /**
     * Тема
     * @property
     * @name SD.Workorder#subject
     * @type {String}
     */
    @Serialize(String) @Parse(String) subject: string;

    /**
     * Подробная информация
     * @property
     * @name SD.Workorder#description
     * @type {String}
     */
    @Serialize(String) @Parse(String) description: string;

    /**
     * Трудозатраты
     * @property
     * @name SD.Workorder#labor
     * @type {Number}
     */
    @Serialize(Nullable(Number)) @Parse(Nullable(Number)) labor: number;

    /**
     * Решение
     * @property
     * @name SD.Workorder#solution
     * @type {String}
     */
    @Serialize(Nullable(String)) @Parse(Nullable(String)) solution: string;

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
    @Serialize(Nullable(serializeId)) @Parse(data => SD.EntityClosureCode.parse(data)) closureCode;

    /**
     * Дата создания
     * @property
     * @name SD.Workorder#createdDate
     * @type {Date}
     */
    @Serialize(Number) @Parse(Instantiate(Date)) createdDate: Date;

    /**
     * Крайний срок
     * @property
     * @name SD.Workorder#deadline
     * @type {Date}
     */
    @Serialize(Number) @Parse(Nullable(Date,"new")) deadline: Date;

    /**
     * Фактически выполнено
     * @property
     * @name SD.Workorder#resolvedDate
     * @type {Date}
     */
    @Serialize(Number) @Parse(Nullable(Date,"new")) resolvedDate: Date;

    /**
     * Дата изменения
     * @property
     * @name SD.Workorder#modifyDate
     * @type {Date}
     */
    @Serialize(Number) @Parse(Nullable(Date,"new")) modifyDate: Date;

    /**
     * Наряд просрочен
     * @property
     * @name SD.Workorder#expired
     * @type {Boolean}
     */
    @Serialize(Boolean) @Parse(Nullable(Boolean)) expired: boolean;

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
    @Serialize((ag,name,mode) => mode == "FULL" ? ag.$serialize() : ag.$modifiedData)
    @Parse(data => SD.EntityAssignment.parse(data)) assignment;

    /**
     * Изменение
     * @property
     * @name SD.Workorder#change
     * @type {SD.Change}
     */
    @Serialize(Nullable(serializeId)) @Parse(data => SD.Change.parse(data)) change;

    /**
     * Проблема
     * @property
     * @name SD.Workorder#problem
     * @type {SD.Problem}
     */
    @Serialize(Nullable(serializeId)) @Parse(data => SD.Problem.parse(data)) problem;

    toString(){
        return String(this.no);
    }
}