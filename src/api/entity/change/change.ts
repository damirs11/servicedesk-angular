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

/** Изменение */
export class Change extends Entity {
  static readonly entityTypeId: EntityTypes = EntityTypes.Change;
  /** Номер */
  public no: number;

  /** Тема */
  public subject: string;

  /** Описание */
  public description: string;

  /** Решение */
  public solution: string;

  /** Статус */
  public status: EntityStatus;

  /** Приоритет */
  public priority: EntityPriority;

  /** Категория */
  public category: EntityCategory;

  /** Классификация */
  public classification: EntityClassification;

  /** Дата создания */
  public createdDate: Date;

  /** Крайний срок */
  public deadline: Date;

  /** Реально начато */
  public actualStart: Date;

  /** Дата фактического выполнения */
  public resolvedDate: Date;

  /** Дата закрытия */
  public closureDate: Date;

  /** План начала */
  public planStart: Date;

  /** План окночания */
  public planFinish: Date;

  /** План продолжительно */
  public planDuration: Date;

  /** Инициатор */
  public initiator: Person;

  /** Менеджер */
  public manager: Person;

  /** Объект обслуживания */
  public configurationItem: ConfigurationItem;

  /** Код завершения */
  public closureCode: EntityClosureCode;

  /** Сущность "назначено" */
  public assignment: EntityAssignment;

  /** Кастомный код 1(Система) */
  system: EntityCode1;

  /** Папка */
  public folder: Folder;

  /** @Override */
  toString(): string {
    return String(this.no);
  }
}
