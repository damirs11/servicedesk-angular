import { Entity } from '../entity/entity';
import { Service } from '../service/service';
import { EntityStatus } from '../entity-status/entity-status';
import { Folder } from '../folder/folder';
import { Workgroup } from '../workgroup/workgroup';
import { EntityPriority } from '../entity-priority/entity-priority';

/** SLA (условие предоставления услуги) */
export class ServiceLevelAgreement extends Entity {
  /** Сервис/услуга */
  public service: Service;

  /** Статус */
  public status: EntityStatus;

  /** Папка */
  public folder: Folder;

  /** Срок действия от */
  public validFrom: Date;

  /** Срок действия до */
  public validTo: Date;

  /** Исполнитель по умолчанию */
  public person: object;

  /** Группа исполнителей по умолчанию */
  public workgroup: Workgroup;

  /** Условия предоставления */
  public serviceLevel: Workgroup;
  /** Приоритет по умолчанию */
  public defaultPriority: EntityPriority;
}
