import { Entity } from '../entity/entity';
import { Service } from '../service/service';
import { EntityStatus } from '../entity-status/entity-status';
import { Folder } from '../folder/folder';
import { Workgroup } from '../workgroup/workgroup';
import { EntityPriority } from '../entity-priority/entity-priority';

/**
 * SLA (условие предоставления услуги)
 * @class
 * @name ServiceLevelAgreement
 */
export class ServiceLevelAgreement extends Entity {
  /**
   * Сервис/услуга
   * @property
   * @name ServiceLevelAgreement#service
   * @type {Service}
   */
  service: Service;

  /**
   * Статус
   * @property
   * @name ServiceLevelAgreement#status
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Папка
   * @property
   * @name ServiceLevelAgreement#folder
   * @type {Folder}
   */
  folder: Folder;

  /**
   * Срок действия от
   * @property
   * @name ServiceLevelAgreement#validFrom
   * @type {Date}
   */
  validFrom: Date;

  /**
   * Срок действия до
   * @property
   * @name ServiceLevelAgreement#validTo
   * @type {Date}
   */
  validTo: Date;

  /**
   * Исполнитель по умолчанию
   * @property
   * @name ServiceLevelAgreement#person
   * @type {object}
   */
  person: object;

  /**
   * Группа исполнителей по умолчанию
   * @property
   * @name ServiceLevelAgreement#workgroup
   * @type {Workgroup}
   */
  workgroup: Workgroup;

  /**
   * Условия предоставления
   * @property
   * @name ServiceLevelAgreement#serviceLevel
   * @type {Workgroup}
   */
  serviceLevel: Workgroup;
  /**
   * Приоритет по умолчанию
   * @property
   * @name ServiceLevelAgreement#defaultPriority
   * @type {EntityPriority}
   */
  defaultPriority: EntityPriority;
}
