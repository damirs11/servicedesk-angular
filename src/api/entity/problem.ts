import {Parse} from './decorator/parse.decorator';
import {Serialize} from './decorator/serialize.decorator';
import {Nullable} from './decorator/parse-utils';
import {serializeId} from './decorator/serialize-utils';
import {Mixin} from './mixin/mixin.decorator';
import {EntityTypes} from './util/entity-types';

/**
 * Сущность - "Проблема"
 * @class
 * @name SD.Problem
 * @mixes ENTITY_MIXIN.Historyable
 * @mixes ENTITY_MIXIN.Approvable
 * @mixes ENTITY_MIXIN.AttachmentsHolder
 * @mixes ENTITY_MIXIN.Accessible
 * @extends SD.EditableEntity
 */
class Problem {
    static $entityTypeId = EntityTypes.Problem;
    /**
     * Номер
     * @property
     * @name SD.Problem#no
     * @type {number}
     */
    @Serialize(Number) no: number;

    /**
     * Статус
     * @property
     * @name SD.Problem#status
     * @type {SD.EntityStatus}
     */
    @Serialize(serializeId)
    status;

    /**
     * Инициатор
     * @property
     * @name SD.Problem#initiator
     * @type {SD.Person}
     */
    @Serialize(serializeId) initiator;

    /**
     * Объект обслуживания
     * @property
     * @name SD.Problem#configurationItem
     * @type {SD.ConfigurationItem}
     */
    @Serialize(Nullable(serializeId)) configurationItem;

    /**
     * Тема
     * @property
     * @name SD.Problem#subject
     * @type {string}
     */
    @Serialize(String) subject: string;

    /**
     * Описание
     * @property
     * @name SD.Problem#description
     * @type {string}
     */
    @Serialize(String) description: string;

    /**
     * Ссылки на логи
     * @property
     * @name SD.Problem#logLinks
     * @type {string}
     */
    @Serialize(String) logLinks: string;

    /**
     * Ссылка на пробелму в jira
     * @property
     * @name SD.Problem#jiraLink
     * @type {string}
     */
    @Serialize(String) jiraLink: string;
    /**
     * @property
     * @name SD.Problem#toVendor
     * @type {string}
     */
    @Serialize(String) toVendor: string;
    /**
     * Обходное решение
     * @property
     * @name SD.Problem#workaround
     * @type {string}
     */
    @Serialize(String) workaround: string;

    /**
     * Решение
     * @property
     * @name SD.Problem#solution
     * @type {string}
     */
    @Serialize(String) solution: string;

    /**
     * Приоритет
     * @property
     * @name SD.Problem#priority
     * @type {SD.EntityPriority}
     */
    @Serialize(serializeId)
    priority;

    /**
     * Крайний срок
     * @property
     * @name SD.Problem#deadline
     * @type {Date}
     */
    @Serialize(Number) deadline: Date;

    /**
     * Дата фактического выполнения
     * @property
     * @name SD.Problem#resolvedDate
     * @type {Date}
     */
    @Serialize(Number) resolvedDate: Date;

    /**
     * Дата закрытия
     * @property
     * @name SD.Problem#closureDate
     * @type {Date}
     */
    @Serialize(Number) closureDate: Date;

    /**
     * Проблема просрочена
     * @property
     * @name SD.Problem#isOverdue
     * @type {Date}
     */
    @Serialize(Boolean) isOverdue: Date;

    /**
     * Персона, что просрочила проблему
     * @property
     * @name SD.Problem#whoOverdue
     * @type {SD.Person}
     */
    @Serialize(serializeId) whoOverdue;

    /**
     * План окночания
     * @property
     * @name SD.Problem#planFinish
     * @type {Date}
     */
    @Serialize(Nullable(Number)) planFinish: Date;

    /**
     * Причина отсрочки
     * @property
     * @name SD.Problem#deferralReason
     * @type {string}
     */
    @Serialize(String) deferralReason: string;

    /**
     * Сущность "назначено"
     * @property
     * @name SD.Problem#assignment
     * @type {SD.EntityAssignment}
     */
    @Serialize((ag, name, mode) => mode === 'FULL' ? ag.$serialize() : ag.$modifiedData)
    assignment;

    /**
     * Категория
     * @property
     * @name SD.Problem#category
     * @type {SD.EntityCategory}
     */
    @Serialize(serializeId) category;

    /**
     * Классификация
     * @property
     * @name SD.Problem#classification
     * @type {SD.EntityClassification}
     */
    @Serialize(serializeId) classification;

    /**
     * Код завершения
     * @property
     * @name SD.Problem#closureCode
     * @type {SD.EntityClosureCode}
     */
    @Serialize(Nullable(serializeId)) closureCode;

    /**
     * Папка
     * @property
     * @name SD.Problem#folder
     * @type {SD.Folder}
     */
    @Serialize(Nullable(serializeId)) folder;

    /**
     * Не включать в отчет заказчику
     * @property
     * @name SD.Problem#isOverdue
     * @type {Date}
     */
    @Serialize(Boolean) notAttachInReport: Date;

    /**
     *
     * @property
     * @name SD.Problem#versionDate
     * @type {Date}
     */
    @Serialize(Number) versionDate: Date;

    toString() {
        return String(this.no);
    }

}
