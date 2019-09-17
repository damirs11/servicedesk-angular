import { Entity } from '../entity/entity';
import { Service } from '../service/service';
import { EntityStatus } from '../entity-status/entity-status';
import { Folder } from '../folder/folder';
import { Workgroup } from '../workgroup/workgroup';
import { EntityPriority } from '../entity-priority/entity-priority';

/**
 * SLA (условие предоставления услуги)
 */
export class ServiceLevelAgreement extends Entity {
  /**
   * Сервис/услуга
   * @property
   * @type {Service}
   */
  service: Service;

  /**
   * Статус
   * @property
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Папка
   * @property
   * @type {Folder}
   */
  folder: Folder;

  /**
   * Срок действия от
   * @property
   * @type {Date}
   */
  validFrom: Date;

  /**
   * Срок действия до
   * @property
   * @type {Date}
   */
  validTo: Date;

  /**
   * Исполнитель по умолчанию
   * @property
   * @type {object}
   */
  person: object;

  /**
   * Группа исполнителей по умолчанию
   * @property
   * @type {Workgroup}
   */
  workgroup: Workgroup;

  /**
   * Условия предоставления
   * @property
   * @type {Workgroup}
   */
  serviceLevel: Workgroup;
  /**
   * Приоритет по умолчанию
   * @property
   * @type {EntityPriority}
   */
  defaultPriority: EntityPriority;
}
