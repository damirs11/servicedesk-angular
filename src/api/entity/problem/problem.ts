import { Entity } from "../entity/entity";
import { EntityTypes } from "src/api/util/entity-types";
import { EntityStatus } from '../entity-status/entity-status';
import { Person } from '../person/person';
import { ConfigurationItem } from '../configuration-item/configuration-item';
import { EntityPriority } from '../entity-priority/entity-priority';
import { EntityAssignment } from '../entity-assignment/entity-assignment';
import { EntityCategory } from '../entity-category/entity-category';
import { EntityClassification } from '../entity-classification/entity-classification';
import { EntityClosureCode } from '../entity-closure-code/entity-closure-code';
import { Folder } from '../folder/folder';

/**
 * Сущность - "Проблема"
  static readonly entityTypeId: EntityTypes = EntityTypes.Problem;
  /**
   * Номер
   * @property
   * @type {number}
   */
  no: number;

  /**
   * Статус
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Инициатор
   * @type {Person}
  initiator: Person;
  /**
   * Объект обслуживания
   * @type {ConfigurationItem}
   */

  /**
   * @type {string}
   */
  subject: string;
  /**
   * Описание
   */
  description: string;

   * Ссылки на логи
   * @type {string}
   */

  /**
   * @type {string}
   */
  jiraLink: string;
  /**
   * @type {string}
  toVendor: string;
  /**
   * Обходное решение
   * @type {string}
  workaround: string;
  /**
   * Решение
   * @type {string}
  solution: string;
  /**
   * Приоритет
   * @type {EntityPriority}
   */

   * Крайний срок
   * @type {Date}
   */
  deadline: Date;
  /**
   * Дата фактического выполнения
   * @type {Date}
   */
  resolvedDate: Date;

   * Дата закрытия
   * @type {Date}
  closureDate: Date;

  /**
   * @type {Date}
   */
  isOverdue: Date;
  /**
   * Персона, что просрочила проблему
   * @type {Person}
   */
  whoOverdue: Person;

  /**
   */
  planFinish: Date;

  /**
   * Причина отсрочки
  deferralReason: string;

  /**
   * Сущность "назначено"
   * @type {EntityAssignment}
   */
  /**
   * Категория
   * @type {EntityCategory}
   */
  category: EntityCategory;
  /**
   * @type {EntityClassification}
   */
  classification: EntityClassification;

   * Код завершения
   */
  closureCode: EntityClosureCode;

  /**
   * @type {Folder}
   */

  /**
   * Не включать в отчет заказчику
   * @type {Date}
   */
  notAttachInReport: Date;

   *
   */
  versionDate: Date;

  /**
   * Override
  toString(): string {
  }
}
