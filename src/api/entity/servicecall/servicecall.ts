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
 * @class
 * @name ServiceCall
 * @mixes ENTITY_MIXIN.Historyable
 * @mixes ENTITY_MIXIN.Accessible
 * @mixes ENTITY_MIXIN.AttachmentsHolder
 * @extends Entity
 */
export class ServiceCall extends Entity {
  static readonly entityTypeId = EntityTypes.ServiceCall;
  /**
   * Номер
   * @property
   * @name ServiceCall#no
   * @type {number}
   */
  no: number;

  /**
   * Тема
   * @property
   * @name ServiceCall#subject
   * @type {string}
   */
  subject: string;

  /**
   * Ext ID
   * @property
   * @name Change#extId
   * @type {string}
   */
  extId: string;

  /**
   * Описание
   * @property
   * @name Change#description
   * @type {string}
   */
  description: string;

  /**
   * Решение
   * @property
   * @name ServiceCall#solution
   * @type {string}
   */
  solution: string;

  /**
   * Статус
   * @property
   * @name ServiceCall#status
   * @type {EntityStatus}
   */
  status: EntityStatus;
  /**
   * Источник
   * @property
   * @name ServiceCall#source
   * @type {Source}
   */
  source: Source;
  /**
   * Время e-mail
   * @property
   * @name ServiceCall#emailDate
   * @type {Date}
   */
  emailDate: Date;
  /**
   * Приоритет
   * @property
   * @name ServiceCall#priority
   * @type {EntityPriority}
   */
  priority: EntityPriority;

  /**
   * Категория
   * @property
   * @name ServiceCall#category
   * @type {EntityCategory}
   */
  category: EntityCategory;

  /**
   * Классификация
   * @property
   * @name ServiceCall#classification
   * @type {EntityClassification}
   */
  classification: EntityClassification;

  /**
   * Крайний срок
   * @property
   * @name ServiceCall#deadline
   * @type {Date}
   */
  deadline: Date;

  /**
   * Дата фактического выполнения
   * @property
   * @name ServiceCall#resolvedDate
   * @type {Date}
   */
  resolvedDate: Date;

  /**
   * Дата закрытия
   * @property
   * @name ServiceCall#closureDate
   * @type {Date}
   */
  closureDate: Date;

  /**
   * Инициатор
   * @property
   * @name ServiceCall#person
   * @type {Person}
   */
  initiator: Person;

  /**
   * Заявитель
   * @property
   * @name ServiceCall#caller
   * @type {Person}
   */
  caller: Person;

  /**
   * Организация
   * @property
   * @name ServiceCall#organization
   * @type {Organization}
   */
  organization: Organization;

  /**
   * SLA
   * @property
   * @name ServiceCall#serviceLevelAgreement
   * @type {ServiceLevelAgreement}
   */
  serviceLevelAgreement: ServiceLevelAgreement;

  /**
   * Сервис/услуга
   * @property
   * @name ServiceCall#service
   * @type {Service}
   */
  service: Service;

  /**
   * Объект обслуживания
   * @property
   * @name ServiceCall#configurationItem
   * @type {ConfigurationItem}
   */
  configurationItem: ConfigurationItem;

  /**
   * Код завершения
   * @property
   * @name ServiceCall#closureCode
   * @type {EntityClosureCode}
   */
  closureCode: EntityClosureCode;

  /**
   * Папка
   * @property
   * @name ServiceCall#folder
   * @type {Folder}
   */
  folder: Folder;

  /**
   * Сущность "назначено"
   * @property
   * @name ServiceCall#assignment
   * @type {EntityAssignment}
   */
  assignment: EntityAssignment;

  /**
   * Дата возобновления
   * @property
   * @name ServiceCall#renewalDate
   * @type {Date}
   */
  renewalDate: Date;

  /**
   * Комментарий по приостановке
   * @property
   * @name ServiceCall#renewalReason
   * @type {string}
   */
  renewalComment: string;

  /**
   * Причина приостановки
   * @property
   * @name ServiceCall#renewalReason
   * @type {EntityCode7}
   */
  renewalReason: EntityCode7;

  /**
   * Новый крайний срок
   * @property
   * @name ServiceCall#newDeadline
   * @type {Date}
   */
  newDeadline: Date;

  /**
   * Причина переноса крайнего срока
   * @property
   * @name ServiceCall#newDeadlineReason
   * @type {string}
   */
  newDeadlineReason: string;
  /**
   * Нарушение регистрации
   * @property
   * @name ServiceCall#registrationError
   * @type {string}
   */
  registrationError: string;
  /**
   * Часто задаваемые вопросы
   * @property
   * @name ServiceCall#frequentlyAskedQuestion
   * @type {string}
   */
  frequentlyAskedQuestion: string;
  /**
   * База известных ошибок
   * @property
   * @name ServiceCall#faq
   * @type {FAQ}
   */
  faq: FAQ;
  /**
   * Руководитель исполнителя
   * @property
   * @name ServiceCall#executorHead
   * @type {Person}
   */
  executorHead: Person;

  /**
   * Подсистема АИС ЭАД
   * @property
   * @name ServiceCall#entityCode6
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
