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
   */
  service: Service;

  /**
   * Статус
   */
  status: EntityStatus;

  /**
   * Папка
   */
  folder: Folder;

  /**
   * Срок действия от
   */
  validFrom: Date;

  /**
   * Срок действия до
   */
  validTo: Date;

  /**
   * Исполнитель по умолчанию
   */
  person: object;

  /**
   * Группа исполнителей по умолчанию
   */
  workgroup: Workgroup;

  /**
   * Условия предоставления
   */
  serviceLevel: Workgroup;
  /**
   * Приоритет по умолчанию
   */
  defaultPriority: EntityPriority;
}
