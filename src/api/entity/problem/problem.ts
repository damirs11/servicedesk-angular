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
 */
export class Problem extends Entity {
  static readonly entityTypeId: EntityTypes = EntityTypes.Problem;
  /**
   * Номер
   */
  no: number;

  /**
   * Статус
   */
  status: EntityStatus;

  /**
   * Инициатор
   */
  initiator: Person;

  /**
   * Объект обслуживания
   */
  configurationItem: ConfigurationItem;

  /**
   * Тема
   */
  subject: string;

  /**
   * Описание
   */
  description: string;

  /**
   * Ссылки на логи
   */
  logLinks: string;

  /**
   * Ссылка на пробелму в jira
   */
  jiraLink: string;
  /**
   */
  toVendor: string;
  /**
   * Обходное решение
   */
  workaround: string;

  /**
   * Решение
   */
  solution: string;

  /**
   * Приоритет
   */
  priority: EntityPriority;

  /**
   * Крайний срок
   */
  deadline: Date;

  /**
   * Дата фактического выполнения
   */
  resolvedDate: Date;

  /**
   * Дата закрытия
   */
  closureDate: Date;

  /**
   * Проблема просрочена
   */
  isOverdue: Date;

  /**
   * Персона, что просрочила проблему
   */
  whoOverdue: Person;

  /**
   * План окночания
   */
  planFinish: Date;

  /**
   * Причина отсрочки
   */
  deferralReason: string;

  /**
   * Сущность "назначено"
   */
  assignment: EntityAssignment;

  /**
   * Категория
   */
  category: EntityCategory;

  /**
   * Классификация
   */
  classification: EntityClassification;

  /**
   * Код завершения
   */
  closureCode: EntityClosureCode;

  /**
   * Папка
   */
  folder: Folder;

  /**
   * Не включать в отчет заказчику
   */
  notAttachInReport: Date;

  /**
   *
   */
  versionDate: Date;

  /** @Override */
  toString(): string {
    return String(this.no);
  }
}
