import { Entity } from "../entity/entity";

/**
 * Объект обслуживания
 */
export class ConfigurationItem extends Entity {
  static entityTypeId = 796000260;
  /**
   * Номер
   */
  no: number;

  /**
   * Код поиска
   */
  searchCode: string;

  /**
   * Дополнительные сведения
   */
  description: string;

  /**
   * Счет-фактура
   */
  orderNr: string;

  /**
   * серийный номер
   */
  serial: string;

  /**
   * Адрес
   */
  address: string;

  /**
   * Замечания
   */
  remark: string;

  /**
   * ip
  ip: string;

  /**
   * Цена
   */
  price: number;

  /**
   * Наличие вложения
   */
  attachmentExists: boolean;

  /**
   * Идентификатор аватарки
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
