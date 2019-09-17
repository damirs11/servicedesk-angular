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
   * Ext ID
   * @type {string}
   */
  extId: string;

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
   * Источник
   * @type {Source}
   */
  source: Source;
  /**
   * Время e-mail
   * @type {Date}
   */
  emailDate: Date;
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
   * Крайний срок
   * @type {Date}
   */
  deadline: Date;

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
   * Инициатор
   * @type {Person}
   */
  initiator: Person;

  /**
   * Заявитель
   * @type {Person}
   */
  caller: Person;

  /**
   * Организация
   * @type {Organization}
   */
  organization: Organization;

  /**
   * SLA
   * @type {ServiceLevelAgreement}
   */
  serviceLevelAgreement: ServiceLevelAgreement;

  /**
   * Сервис/услуга
   * @type {Service}
   */
  service: Service;

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
   * Папка
   * @type {Folder}
   */
  folder: Folder;

  /**
   * Сущность "назначено"
   * @type {EntityAssignment}
   */
  assignment: EntityAssignment;

  /**
   * Дата возобновления
   * @type {Date}
   */
  renewalDate: Date;

  /**
   * Комментарий по приостановке
   * @type {string}
   */
  renewalComment: string;

  /**
   * Причина приостановки
   * @type {EntityCode7}
   */
  renewalReason: EntityCode7;

  /**
   * Новый крайний срок
   * @type {Date}
   */
  newDeadline: Date;

  /**
   * Причина переноса крайнего срока
   * @type {string}
   */
  newDeadlineReason: string;
  /**
   * Нарушение регистрации
   * @type {string}
   */
  registrationError: string;
  /**
   * Часто задаваемые вопросы
   * @type {string}
   */
  frequentlyAskedQuestion: string;
  /**
   * База известных ошибок
   * @type {FAQ}
   */
  faq: FAQ;
  /**
   * Руководитель исполнителя
   * @type {Person}
   */
  executorHead: Person;

  /**
   * Подсистема АИС ЭАД
   * @type {EntityCode6}
   */
  entityCode6: EntityCode6;

  /**
   * Override
   */
  toString(): string {
    return String(this.no);
  }
}
