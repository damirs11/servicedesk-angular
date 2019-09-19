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


/**
 * Заявка
 */
export class ServiceCall extends Entity {
  static readonly entityTypeId = EntityTypes.ServiceCall;
  /**
   * Номер
   */
  no: number;

  /**
   * Тема
   */
  subject: string;

  /**
   * Ext ID
   */
  extId: string;

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
   * Источник
   */
  source: Source;
  /**
   * Время e-mail
   */
  emailDate: Date;
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
   * Инициатор
   */
  initiator: Person;

  /**
   * Заявитель
   */
  caller: Person;

  /**
   * Организация
   */
  organization: Organization;

  /**
   * SLA
   */
  serviceLevelAgreement: ServiceLevelAgreement;

  /**
   * Сервис/услуга
   */
  service: Service;

  /**
   * Объект обслуживания
   */
  configurationItem: ConfigurationItem;

  /**
   * Код завершения
   */
  closureCode: EntityClosureCode;

  /**
   * Папка
   */
  folder: Folder;

  /**
   * Сущность "назначено"
   */
  assignment: EntityAssignment;

  /**
   * Дата возобновления
   */
  renewalDate: Date;

  /**
   * Комментарий по приостановке
   */
  renewalComment: string;

  /**
   * Причина приостановки
   */
  renewalReason: EntityCode7;

  /**
   * Новый крайний срок
   */
  newDeadline: Date;

  /**
   * Причина переноса крайнего срока
   */
  newDeadlineReason: string;
  /**
   * Нарушение регистрации
   */
  registrationError: string;
  /**
   * Часто задаваемые вопросы
   */
  frequentlyAskedQuestion: string;
  /**
   * База известных ошибок
   */
  faq: FAQ;
  /**
   * Руководитель исполнителя
   */
  executorHead: Person;

  /**
   * Подсистема АИС ЭАД
   */
  entityCode6: EntityCode6;

  /** @Override */
  toString(): string {
    return String(this.no);
  }
}
