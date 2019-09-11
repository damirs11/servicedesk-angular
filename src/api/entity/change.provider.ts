import {Parse} from './decorator/parse.decorator';
import {Serialize} from './decorator/serialize.decorator';
import {Nullable} from './decorator/parse-utils';
import {serializeId} from './decorator/serialize-utils';
import {Mixin} from './mixin/mixin.decorator';
import {EntityTypes} from './util/entity-types';

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
export class Change {
    static $entityTypeId = EntityTypes.Change;
    /**
     * Номер
     * @property
     * @name SD.Change#no
     * @type {number}
     */
    @Serialize(Number) no: number;

    /**
     * Тема
     * @property
     * @name SD.Change#subject
     * @type {string}
     */
    @Serialize(String) subject: string;

    /**
     * Описание
     * @property
     * @name SD.Change#description
     * @type {string}
     */
    @Serialize(String) description: string;

    /**
     * Решение
     * @property
     * @name SD.Change#solution
     * @type {string}
     */
    @Serialize(String) solution: string;

    /**
     * Статус
     * @property
     * @name SD.Change#status
     * @type {SD.EntityStatus}
     */
    @Serialize(serializeId)
    status;

    /**
     * Приоритет
     * @property
     * @name SD.Change#priority
     * @type {SD.EntityPriority}
     */
    @Serialize(serializeId)
    priority;

    /**
     * Категория
     * @property
     * @name SD.Change#category
     * @type {SD.EntityCategory}
     */
    @Serialize(serializeId) category;

    /**
     * Классификация
     * @property
     * @name SD.Change#classification
     * @type {SD.EntityClassification}
     */
    @Serialize(serializeId) classification;

    /**
     * Дата создания
     * @property
     * @name SD.Change#createdDate
     * @type {Date}
     */
    @Serialize(Number) createdDate: Date;

    /**
     * Крайний срок
     * @property
     * @name SD.Change#deadline
     * @type {Date}
     */
    @Serialize(Number) deadline: Date;

    /**
     * Реально начато
     * @property
     * @name SD.Change#actualStart
     * @type {Date}
     */
    @Serialize(Number) actualStart: Date;

    /**
     * Дата фактического выполнения
     * @property
     * @name SD.Change#resolvedDate
     * @type {Date}
     */
    @Serialize(Number) resolvedDate: Date;

    /**
     * Дата закрытия
     * @property
     * @name SD.Change#closureDate
     * @type {Date}
     */
    @Serialize(Number) closureDate: Date;

    /**
     * План начала
     * @property
     * @name SD.Change#planStart
     * @type {Date}
     */
    @Serialize(Nullable(Number)) planStart: Date;

    /**
     * План окночания
     * @property
     * @name SD.Change#planFinish
     * @type {Date}
     */
    @Serialize(Nullable(Number)) planFinish: Date;

    /**
     * План продолжительно
     * @property
     * @name SD.Change#planDuration
     * @type {Date}
     */
    @Serialize(Nullable(Number)) planDuration: Date;

    /**
     * Инициатор
     * @property
     * @name SD.Change#person
     * @type {SD.Person}
     */
    @Serialize(serializeId) initiator;

    /**
     * Менеджер
     * @property
     * @name SD.Change#manager
     * @type {SD.Person}
     */
    @Serialize(serializeId) manager;

    /**
     * Объект обслуживания
     * @property
     * @name SD.Change#configurationItem
     * @type {SD.ConfigurationItem}
     */
    @Serialize(Nullable(serializeId)) configurationItem;

    /**
     * Код завершения
     * @property
     * @name SD.Change#closureCode
     * @type {SD.EntityClosureCode}
     */
    @Serialize(Nullable(serializeId)) closureCode;

    /**
     * Сущность "назначено"
     * @property
     * @name SD.Change#assignment
     * @type {SD.EntityAssignment}
     */
    @Serialize((ag, name, mode) => mode === 'FULL' ? ag.$serialize() : ag.$modifiedData)
    assignment;

    /**
     * Кастомный код 1(Система)
     * @property
     * @name SD.Change#entityCode1
     * @type {SD.EntityCode1}
     */
    @Serialize(Nullable(serializeId)) system;

    /**
     * Папка
     * @property
     * @name SD.Change#folder
     * @type {SD.Folder}
     */
    @Serialize(Nullable(serializeId)) folder;

    toString() {
        return String(this.no);
    }
}
