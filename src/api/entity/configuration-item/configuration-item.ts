import { Entity } from "../entity/entity";

/**
 * Объект обслуживания
 */
export class ConfigurationItem extends Entity {
  static entityTypeId = 796000260;
  /**
   * Номер
   * @property
   * @type {number}
   */
  no: number;

  /**
   * Код поиска
   * @property
   * @type {string}
   */
  searchCode: string;

  /**
   * Дополнительные сведения
   * @property
   * @type {string}
   */
  description: string;

  /**
   * Счет-фактура
   * @property
   * @type {string}
   */
  orderNr: string;

  /**
   * серийный номер
   * @property
   * @type {string}
   */
  serial: string;

  /**
   * Адрес
   * @property
   * @type {string}
   */
  address: string;

  /**
   * Замечания
   * @property
   * @type {string}
   */
  remark: string;

  /**
   * ip
   * @property
   * @type {string}
   */
  ip: string;

  /**
   * Цена
   * @property
   * @type {number}
   */
  price: number;

  /**
   * Наличие вложения
   * @property
   * @type {boolean}
   */
  attachmentExists: boolean;

  /**
   * Идентификатор аватарки
   * @property
   * @type {string}
   */
  avatarId: string;

  status: any;

  category: any;
  /**
   * Override
   */
  toString(): string {
    return this.searchCode;
  }
}
