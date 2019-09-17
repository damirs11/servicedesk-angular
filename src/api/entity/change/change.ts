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
 * @mixes ENTITY_MIXIN.Accessible
 */
export class Change extends Entity {
  static readonly entityTypeId: EntityTypes = EntityTypes.Change;
  /**
   * Номер
   * @property
   * @type {number}
   */
  no: number;

  /**
   * Тема
   * @property
   * @type {string}
   */
  subject: string;

  /**
   * Описание
   * @property
   * @type {string}
   */
  description: string;

  /**
   * Решение
   * @property
   * @type {string}
   */
  solution: string;

  /**
   * Статус
   * @property
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Приоритет
   * @property
   * @type {EntityPriority}
   */
  priority: EntityPriority;

  /**
   * Категория
   * @property
   * @type {EntityCategory}
   */
  category: EntityCategory;

  /**
   * Классификация
   * @property
   * @type {EntityClassification}
   */
  classification: EntityClassification;

  /**
   * Дата создания
   * @property
   * @type {Date}
   */
  createdDate: Date;

  /**
   * Крайний срок
   * @property
   * @type {Date}
   */
  deadline: Date;

  /**
   * Реально начато
   * @property
   * @type {Date}
   */
  actualStart: Date;

  /**
   * Дата фактического выполнения
   * @property
   * @type {Date}
   */
  resolvedDate: Date;

  /**
   * Дата закрытия
   * @property
   * @type {Date}
   */
  closureDate: Date;

  /**
   * План начала
   * @property
   * @type {Date}
   */
  planStart: Date;

  /**
   * План окночания
   * @property
   * @type {Date}
   */
  planFinish: Date;

  /**
   * План продолжительно
   * @property
   * @type {Date}
   */
  planDuration: Date;

  /**
   * Инициатор
   * @property
   * @type {Person}
   */
  initiator: Person;

  /**
   * Менеджер
   * @property
   * @type {Person}
   */
  manager: Person;

  /**
   * Объект обслуживания
   * @property
   * @type {ConfigurationItem}
   */
  configurationItem: ConfigurationItem;

  /**
   * Код завершения
   * @property
   * @type {EntityClosureCode}
   */
  closureCode: EntityClosureCode;

  /**
   * Сущность "назначено"
   * @property
   * @type {EntityAssignment}
   */
  assignment: EntityAssignment;

  /**
   * Кастомный код 1(Система)
   * @property
   * @type {EntityCode1}
   */
  system: EntityCode1;

  /**
   * Папка
   * @property
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
