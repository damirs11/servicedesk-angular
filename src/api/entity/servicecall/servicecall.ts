import { Entity } from '../entity/entity';
import { EntityTypes } from 'src/api/util/entity-types';
import { EntityStatus } from '../entity-status/entity-status';
import { Source } from '../source/source';
import { EntityPriority } from '../entity-priority/entity-priority';
import { EntityCategory } from '../entity-category/entity-category';
import { EntityClassification } from '../entity-classification/entity-classification';
import { Person } from '../person/person';
import { Organization } from '../organization/organization';
import { ServiceLevelAgreement } from '../sla/sla';
import { Service } from '../service/service';
import { ConfigurationItem } from '../configuration-item/configuration-item';
import { EntityClosureCode } from '../entity-closure-code/entity-closure-code';
import { Folder } from '../folder/folder';
import { EntityAssignment } from '../entity-assignment/entity-assignment';
import { EntityCode7 } from '../entity-code7/entity-code7';
import { FAQ } from '../faq/faq';
import { EntityCode6 } from '../entity-code6/entity-code6';


/** Заявка */
export class ServiceCall extends Entity {
  static readonly entityTypeId = EntityTypes.ServiceCall;
  /** Номер */
  public no: number;

  /** Тема */
  public subject: string;

  /** Ext ID */
  public extId: string;

  /** Описание */
  public description: string;

  /** Решение */
  public solution: string;

  /** Статус */
  public status: EntityStatus;
  /** Источник */
  public source: Source;
  /** Время e-mail */
  public emailDate: Date;
  /** Приоритет */
  public priority: EntityPriority;

  /** Категория */
  public category: EntityCategory;

  /** Классификация */
  public classification: EntityClassification;

  /** Крайний срок */
  public deadline: Date;

  /** Дата фактического выполнения */
  public resolvedDate: Date;

  /** Дата закрытия */
  public closureDate: Date;

  /** Инициатор */
  public initiator: Person;

  /** Заявитель */
  public caller: Person;

  /** Организация */
  public organization: Organization;

  /** SLA */
  public serviceLevelAgreement: ServiceLevelAgreement;

  /** Сервис/услуга */
  public service: Service;

  /** Объект обслуживания */
  public configurationItem: ConfigurationItem;

  /** Код завершения */
  public closureCode: EntityClosureCode;

  /** Папка */
  public folder: Folder;

  /** Сущность "назначено" */
  public assignment: EntityAssignment;

  /** Дата возобновления */
  public renewalDate: Date;

  /** Комментарий по приостановке */
  public renewalComment: string;

  /** Причина приостановки */
  renewalReason: EntityCode7;

  /** Новый крайний срок */
  public newDeadline: Date;

  /** Причина переноса крайнего срока */
  public newDeadlineReason: string;
  
  /** Нарушение регистрации */
  public registrationError: string;

  /** Часто задаваемые вопросы */
  public frequentlyAskedQuestion: string;

  /** База известных ошибок */
  public faq: FAQ;

  /** Руководитель исполнителя */
  public executorHead: Person;

  /** Подсистема АИС ЭАД */
  entityCode6: EntityCode6;

  /** @Override */
  toString(): string {
    return String(this.no);
  }
}
