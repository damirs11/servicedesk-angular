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
   * @property
   * @type {string}
   */
  subject: string;

  /**
   * Ext ID
   * @property
   * @type {string}
   */
  extId: string;

  /**
   * Описание
   * @property
   * @type {string}
   */
  description: string;

  /**
   * Решение
   * @property
   * @type {string}
   */
  solution: string;

  /**
   * Статус
   * @property
   * @type {EntityStatus}
   */
  status: EntityStatus;
  /**
   * Источник
   * @property
   * @type {Source}
   */
  source: Source;
  /**
   * Время e-mail
   * @property
   * @type {Date}
   */
  emailDate: Date;
  /**
   * Приоритет
   * @property
   * @type {EntityPriority}
   */
  priority: EntityPriority;

  /**
   * Категория
   * @property
   * @type {EntityCategory}
   */
  category: EntityCategory;

  /**
   * Классификация
   * @property
   * @type {EntityClassification}
   */
  classification: EntityClassification;

  /**
   * Крайний срок
   * @property
   * @type {Date}
   */
  deadline: Date;

  /**
   * Дата фактического выполнения
   * @property
   * @type {Date}
   */
  resolvedDate: Date;

  /**
   * Дата закрытия
   * @property
   * @type {Date}
   */
  closureDate: Date;

  /**
   * Инициатор
   * @property
   * @type {Person}
   */
  initiator: Person;

  /**
   * Заявитель
   * @property
   * @type {Person}
   */
  caller: Person;

  /**
   * Организация
   * @property
   * @type {Organization}
   */
  organization: Organization;

  /**
   * SLA
   * @property
   * @type {ServiceLevelAgreement}
   */
  serviceLevelAgreement: ServiceLevelAgreement;

  /**
   * Сервис/услуга
   * @property
   * @type {Service}
   */
  service: Service;

  /**
   * Объект обслуживания
   * @property
   * @type {ConfigurationItem}
   */
  configurationItem: ConfigurationItem;

  /**
   * Код завершения
   * @property
   * @type {EntityClosureCode}
   */
  closureCode: EntityClosureCode;

  /**
   * Папка
   * @property
   * @type {Folder}
   */
  folder: Folder;

  /**
   * Сущность "назначено"
   * @property
   * @type {EntityAssignment}
   */
  assignment: EntityAssignment;

  /**
   * Дата возобновления
   * @property
   * @type {Date}
   */
  renewalDate: Date;

  /**
   * Комментарий по приостановке
   * @property
   * @type {string}
   */
  renewalComment: string;

  /**
   * Причина приостановки
   * @property
   * @type {EntityCode7}
   */
  renewalReason: EntityCode7;

  /**
   * Новый крайний срок
   * @property
   * @type {Date}
   */
  newDeadline: Date;

  /**
   * Причина переноса крайнего срока
   * @property
   * @type {string}
   */
  newDeadlineReason: string;
  /**
   * Нарушение регистрации
   * @property
   * @type {string}
   */
  registrationError: string;
  /**
   * Часто задаваемые вопросы
   * @property
   * @type {string}
   */
  frequentlyAskedQuestion: string;
  /**
   * База известных ошибок
   * @property
   * @type {FAQ}
   */
  faq: FAQ;
  /**
   * Руководитель исполнителя
   * @property
   * @type {Person}
   */
  executorHead: Person;

  /**
   * Подсистема АИС ЭАД
   * @property
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
