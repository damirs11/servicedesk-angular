import {Serialize} from './decorator/serialize.decorator';
import {Instantiate, Nullable} from './decorator/parse-utils';
import {serializeId} from './decorator/serialize-utils';
import {Mixin} from './mixin/mixin.decorator';
import {EntityTypes} from './util/entity-types';

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
    @Serialize(Number) no: number;

    /**
     * Тема
     * @property
     * @name SD.Workorder#subject
     * @type {String}
     */
    @Serialize(String)  subject: string;

    /**
     * Подробная информация
     * @property
     * @name SD.Workorder#description
     * @type {String}
     */
    @Serialize(String) description: string;

    /**
     * Трудозатраты
     * @property
     * @name SD.Workorder#labor
     * @type {Number}
     */
    @Serialize(Nullable(Number)) labor: number;

    /**
     * Решение
     * @property
     * @name SD.Workorder#solution
     * @type {String}
     */
    @Serialize(Nullable(String)) solution: string;

    /**
     * Статус
     * @property
     * @name SD.Workorder#status
     * @type {SD.EntityStatus}
     */
    @Serialize(serializeId) status;

    /**
     * Категория
     * @property
     * @name SD.Workorder#category
     * @type {SD.EntityCategory}
     */
    @Serialize(serializeId)  category;

    /**
     * Код завершения
     * @property
     * @name SD.Workorder#closureCode
     * @type {SD.EntityClosureCode}
     */
    @Serialize(Nullable(serializeId))  closureCode;

    /**
     * Дата создания
     * @property
     * @name SD.Workorder#createdDate
     * @type {Date}
     */
    @Serialize(Number)  createdDate: Date;

    /**
     * Крайний срок
     * @property
     * @name SD.Workorder#deadline
     * @type {Date}
     */
    @Serialize(Number)  deadline: Date;

    /**
     * Фактически выполнено
     * @property
     * @name SD.Workorder#resolvedDate
     * @type {Date}
     */
    @Serialize(Number)  resolvedDate: Date;

    /**
     * Дата изменения
     * @property
     * @name SD.Workorder#modifyDate
     * @type {Date}
     */
    @Serialize(Number)  modifyDate: Date;

    /**
     * Наряд просрочен
     * @property
     * @name SD.Workorder#expired
     * @type {Boolean}
     */
    @Serialize(Boolean)  expired: boolean;

    /**
     * Папка
     * @property
     * @name SD.Workorder#folder
     * @type {SD.Folder}
     */
    @Serialize(Nullable(serializeId))  folder;

    /**
     * Инициатор
     * @property
     * @name SD.Workorder#initiator
     * @type {SD.Person}
     */
    @Serialize(serializeId)  initiator;

    /**
     * Объект "Назначено"
     * @property
     * @name SD.Workorder#assignment
     * @type {SD.EntityAssignment}
     */
    @Serialize((ag, name, mode) => mode === 'FULL' ? ag.$serialize() : ag.$modifiedData)
     assignment;

    /**
     * Изменение
     * @property
     * @name SD.Workorder#change
     * @type {SD.Change}
     */
    @Serialize(Nullable(serializeId))  change;

    /**
     * Проблема
     * @property
     * @name SD.Workorder#problem
     * @type {SD.Problem}
     */
    @Serialize(Nullable(serializeId))  problem;

    toString() {
        return String(this.no);
    }
}
