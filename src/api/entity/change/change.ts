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
 */
export class Change extends Entity {
  static readonly entityTypeId: EntityTypes = EntityTypes.Change;
  /**
   * Номер
   */
  no: number;

  /**
   * Тема
   */
  subject: string;

  /**
   * Описание
   */
  description: string;

  /**
   * Решение
   */
  solution: string;

  /**
   * Статус
   */
  status: EntityStatus;

  /**
   * Приоритет
   */
  priority: EntityPriority;

  /**
   * Категория
   */
  category: EntityCategory;

  /**
   * Классификация
   */
  classification: EntityClassification;

  /**
   * Дата создания
   */
  createdDate: Date;

  /**
   * Крайний срок
   */
  deadline: Date;

  /**
   * Реально начато
   */
  actualStart: Date;

  /**
   * Дата фактического выполнения
   */
  resolvedDate: Date;

  /**
   * Дата закрытия
   */
  closureDate: Date;

  /**
   * План начала
   */
  planStart: Date;

  /**
   * План окночания
   */
  planFinish: Date;

  /**
   * План продолжительно
   */
  planDuration: Date;

  /**
   * Инициатор
   */
  initiator: Person;

  /**
   * Менеджер
   */
  manager: Person;

  /**
   * Объект обслуживания
   */
  configurationItem: ConfigurationItem;

  /**
   * Код завершения
   */
  closureCode: EntityClosureCode;

  /**
   * Сущность "назначено"
   */
  assignment: EntityAssignment;

  /**
   * Кастомный код 1(Система)
   */
  system: EntityCode1;

  /**
   * Папка
   */
  folder: Folder;

  /**
   * Override
   */
  toString(): string {
    return String(this.no);
  }
}
