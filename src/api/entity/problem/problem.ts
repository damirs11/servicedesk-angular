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
   * @property
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Инициатор
   * @property
   * @type {Person}
  initiator: Person;
  /**
   * Объект обслуживания
   * @property
   * @type {ConfigurationItem}
   */

  /**
   * @property
   * @type {string}
   */
  subject: string;
  /**
   * Описание
   * @property
   */
  description: string;

   * Ссылки на логи
   * @property
   * @type {string}
   */

  /**
   * @property
   * @type {string}
   */
  jiraLink: string;
  /**
   * @type {string}
  toVendor: string;
  /**
   * Обходное решение
   * @property
   * @type {string}
  workaround: string;
  /**
   * Решение
   * @property
   * @type {string}
  solution: string;
  /**
   * Приоритет
   * @property
   * @type {EntityPriority}
   */

   * Крайний срок
   * @property
   * @type {Date}
   */
  deadline: Date;
  /**
   * Дата фактического выполнения
   * @type {Date}
   */
  resolvedDate: Date;

   * Дата закрытия
   * @property
   * @type {Date}
  closureDate: Date;

  /**
   * @property
   * @type {Date}
   */
  isOverdue: Date;
  /**
   * Персона, что просрочила проблему
   * @type {Person}
   */
  whoOverdue: Person;

  /**
   * @property
   */
  planFinish: Date;

  /**
   * Причина отсрочки
   * @property
  deferralReason: string;

  /**
   * Сущность "назначено"
   * @property
   * @type {EntityAssignment}
   */
  /**
   * Категория
   * @property
   * @type {EntityCategory}
   */
  category: EntityCategory;
  /**
   * @property
   * @type {EntityClassification}
   */
  classification: EntityClassification;

   * Код завершения
   * @property
   */
  closureCode: EntityClosureCode;

  /**
   * @property
   * @type {Folder}
   */

  /**
   * Не включать в отчет заказчику
   * @type {Date}
   */
  notAttachInReport: Date;

   *
   * @property
   */
  versionDate: Date;

  /**
   * Override
  toString(): string {
  }
}
