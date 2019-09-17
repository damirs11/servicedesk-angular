import { Entity } from "../entity/entity";
import { EntityTypes } from "src/api/util/entity-types";
import { EntityStatus } from "../entity-status/entity-status";
import { EntityPriority } from "../entity-priority/entity-priority";
import { EntityCategory } from "../entity-category/entity-category";
import { EntityClassification } from "../entity-classification/entity-classification";
import { ConfigurationItem } from "../configuration-item/configuration-item";
import { EntityClosureCode } from "../entity-closure-code/entity-closure-code";
import { EntityAssignment } from "../entity-assignment/entity-assignment";
import { EntityCode1 } from "../entity-code1/entity-code1";
import { Folder } from "../folder/folder";
import { Person } from '../person/person';

/**
 * Изменение
 * @class
 * @name Change
 * @mixes ENTITY_MIXIN.Historyable
 * @mixes ENTITY_MIXIN.Approvable
 * @mixes ENTITY_MIXIN.AttachmentsHolder
 * @mixes ENTITY_MIXIN.Accessible
 * @extends Entity
 */
export class Change extends Entity {
  static readonly entityTypeId: EntityTypes = EntityTypes.Change;
  /**
   * Номер
   * @property
   * @name Change#no
   * @type {number}
   */
  no: number;

  /**
   * Тема
   * @property
   * @name Change#subject
   * @type {string}
   */
  subject: string;

  /**
   * Описание
   * @property
   * @name Change#description
   * @type {string}
   */
  description: string;

  /**
   * Решение
   * @property
   * @name Change#solution
   * @type {string}
   */
  solution: string;

  /**
   * Статус
   * @property
   * @name Change#status
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Приоритет
   * @property
   * @name Change#priority
   * @type {EntityPriority}
   */
  priority: EntityPriority;

  /**
   * Категория
   * @property
   * @name Change#category
   * @type {EntityCategory}
   */
  category: EntityCategory;

  /**
   * Классификация
   * @property
   * @name Change#classification
   * @type {EntityClassification}
   */
  classification: EntityClassification;

  /**
   * Дата создания
   * @property
   * @name Change#createdDate
   * @type {Date}
   */
  createdDate: Date;

  /**
   * Крайний срок
   * @property
   * @name Change#deadline
   * @type {Date}
   */
  deadline: Date;

  /**
   * Реально начато
   * @property
   * @name Change#actualStart
   * @type {Date}
   */
  actualStart: Date;

  /**
   * Дата фактического выполнения
   * @property
   * @name Change#resolvedDate
   * @type {Date}
   */
  resolvedDate: Date;

  /**
   * Дата закрытия
   * @property
   * @name Change#closureDate
   * @type {Date}
   */
  closureDate: Date;

  /**
   * План начала
   * @property
   * @name Change#planStart
   * @type {Date}
   */
  planStart: Date;

  /**
   * План окночания
   * @property
   * @name Change#planFinish
   * @type {Date}
   */
  planFinish: Date;

  /**
   * План продолжительно
   * @property
   * @name Change#planDuration
   * @type {Date}
   */
  planDuration: Date;

  /**
   * Инициатор
   * @property
   * @name Change#person
   * @type {Person}
   */
  initiator: Person;

  /**
   * Менеджер
   * @property
   * @name Change#manager
   * @type {Person}
   */
  manager: Person;

  /**
   * Объект обслуживания
   * @property
   * @name Change#configurationItem
   * @type {ConfigurationItem}
   */
  configurationItem: ConfigurationItem;

  /**
   * Код завершения
   * @property
   * @name Change#closureCode
   * @type {EntityClosureCode}
   */
  closureCode: EntityClosureCode;

  /**
   * Сущность "назначено"
   * @property
   * @name Change#assignment
   * @type {EntityAssignment}
   */
  assignment: EntityAssignment;

  /**
   * Кастомный код 1(Система)
   * @property
   * @name Change#entityCode1
   * @type {EntityCode1}
   */
  system: EntityCode1;

  /**
   * Папка
   * @property
   * @name Change#folder
   * @type {Folder}
   */
  folder: Folder;

  /**
   * Override
   */
  toString(): string {
    return String(this.no);
  }
}
