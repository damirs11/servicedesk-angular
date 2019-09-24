import { Entity } from "../entity/entity";

/** Объект обслуживания */
export class ConfigurationItem extends Entity {
  static readonly entityTypeId = 796000260;
  /** Номер */
  public no: number;

  /** Код поиска */
  public searchCode: string;

  /** Дополнительные сведения */
  public description: string;

  /** Счет-фактура */
  public orderNr: string;

  /** серийный номер */
  public serial: string;

  /** Адрес */
  public address: string;

  /** Замечания */
  public remark: string;

  /** ip */
  public ip: string;

  /** Цена */
  public price: number;

  /** Наличие вложения */
  public attachmentExists: boolean;

  /** Идентификатор аватарки */
  public avatarId: string;

  public status: any;

  public category: any;
  
  /** @Override */
  toString(): string {
    return this.searchCode;
  }
}
