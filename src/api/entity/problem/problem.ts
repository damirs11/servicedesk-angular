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

/** Сущность - "Проблема" */
export class Problem extends Entity {
  static readonly entityTypeId: EntityTypes = EntityTypes.Problem;
  /** Номер */
  public no: number;

  /** Статус */
  public status: EntityStatus;

  /** Инициатор */
  public initiator: Person;

  /** Объект обслуживания */
  public configurationItem: ConfigurationItem;

  /** Тема */
  public subject: string;

  /** Описание */
  public description: string;

  /** Ссылки на логи */
  public logLinks: string;

  /** Ссылка на пробелму в jira */
  public jiraLink: string;
  /**
   */
  public toVendor: string;
  /** Обходное решение */
  public workaround: string;

  /** Решение */
  public solution: string;

  /** Приоритет */
  public priority: EntityPriority;

  /** Крайний срок */
  public deadline: Date;

  /** Дата фактического выполнения */
  public resolvedDate: Date;

  /** Дата закрытия */
  public closureDate: Date;

  /** Проблема просрочена */
  public isOverdue: Date;

  /** Персона, что просрочила проблему */
  public whoOverdue: Person;

  /** План окночания */
  public planFinish: Date;

  /** Причина отсрочки */
  public deferralReason: string;

  /** Сущность "назначено" */
  public assignment: EntityAssignment;

  /** Категория */
  public category: EntityCategory;

  /** Классификация */
  public classification: EntityClassification;

  /** Код завершения */
  public closureCode: EntityClosureCode;

  /** Папка */
  public folder: Folder;

  /** Не включать в отчет заказчику */
  public notAttachInReport: Date;

  /**  */
  public versionDate: Date;

  /** @Override */
  toString(): string {
    return String(this.no);
  }
}
