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
   * @type {string}
   */
  searchCode: string;

  /**
   * Дополнительные сведения
   * @type {string}
   */
  description: string;

  /**
   * Счет-фактура
   * @type {string}
   */
  orderNr: string;

  /**
   * серийный номер
   * @type {string}
   */
  serial: string;

  /**
   * Адрес
   * @type {string}
   */
  address: string;

  /**
   * Замечания
   * @type {string}
   */
  remark: string;

  /**
   * ip
   * @type {string}
   */
  ip: string;

  /**
   * Цена
   * @type {number}
   */
  price: number;

  /**
   * Наличие вложения
   * @type {boolean}
   */
  attachmentExists: boolean;

  /**
   * Идентификатор аватарки
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
