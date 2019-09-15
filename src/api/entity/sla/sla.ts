import { Entity } from '../entity/entity';

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
  service;

  /**
   * Статус
   * @property
   * @name ServiceLevelAgreement#status
   * @type {EntityStatus}
   */
  status;

  /**
   * Название
   * @property
   * @name ServiceLevelAgreement#name
   * @type {string}
   */
  name: string;

  /**
   * Папка
   * @property
   * @name ServiceLevelAgreement#folder
   * @type {Folder}
   */
  folder;

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
  workgroup;

  /**
   * Условия предоставления
   * @property
   * @name ServiceLevelAgreement#serviceLevel
   * @type {Workgroup}
   */
  serviceLevel;
  /**
   * Приоритет по умолчанию
   * @property
   * @name ServiceLevelAgreement#defaultPriority
   * @type {EntityPriority}
   */
  defaultPriority;
  toString() {
    return String(this.name);
  }
}
