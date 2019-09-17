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
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Папка
   * @type {Folder}
   */
  folder: Folder;

  /**
   * Срок действия от
   * @type {Date}
   */
  validFrom: Date;

  /**
   * Срок действия до
   * @type {Date}
   */
  validTo: Date;

  /**
   * Исполнитель по умолчанию
   * @type {object}
   */
  person: object;

  /**
   * Группа исполнителей по умолчанию
   * @type {Workgroup}
   */
  workgroup: Workgroup;

  /**
   * Условия предоставления
   * @type {Workgroup}
   */
  serviceLevel: Workgroup;
  /**
   * Приоритет по умолчанию
   * @type {EntityPriority}
   */
  defaultPriority: EntityPriority;
}
