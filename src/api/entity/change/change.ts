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
   * @type {string}
   */
  subject: string;

  /**
   * Описание
   * @type {string}
   */
  description: string;

  /**
   * Решение
   * @type {string}
   */
  solution: string;

  /**
   * Статус
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Приоритет
   * @type {EntityPriority}
   */
  priority: EntityPriority;

  /**
   * Категория
   * @type {EntityCategory}
   */
  category: EntityCategory;

  /**
   * Классификация
   * @type {EntityClassification}
   */
  classification: EntityClassification;

  /**
   * Дата создания
   * @type {Date}
   */
  createdDate: Date;

  /**
   * Крайний срок
   * @type {Date}
   */
  deadline: Date;

  /**
   * Реально начато
   * @type {Date}
   */
  actualStart: Date;

  /**
   * Дата фактического выполнения
   * @type {Date}
   */
  resolvedDate: Date;

  /**
   * Дата закрытия
   * @type {Date}
   */
  closureDate: Date;

  /**
   * План начала
   * @type {Date}
   */
  planStart: Date;

  /**
   * План окночания
   * @type {Date}
   */
  planFinish: Date;

  /**
   * План продолжительно
   * @type {Date}
   */
  planDuration: Date;

  /**
   * Инициатор
   * @type {Person}
   */
  initiator: Person;

  /**
   * Менеджер
   * @type {Person}
   */
  manager: Person;

  /**
   * Объект обслуживания
   * @type {ConfigurationItem}
   */
  configurationItem: ConfigurationItem;

  /**
   * Код завершения
   * @type {EntityClosureCode}
   */
  closureCode: EntityClosureCode;

  /**
   * Сущность "назначено"
   * @type {EntityAssignment}
   */
  assignment: EntityAssignment;

  /**
   * Кастомный код 1(Система)
   * @type {EntityCode1}
   */
  system: EntityCode1;

  /**
   * Папка
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
