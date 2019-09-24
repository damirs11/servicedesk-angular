import { Entity } from '../entity/entity';
import { ImpactSettings } from '../impact-settings/impact-settings';

/** Условия предоставления */
export class ServiceLevel extends Entity {
  /** Название */
  public description: string;

  /** Значение по умолчанию (может быть только один среди всех условий) */
  public defaultValue: boolean;

  /** Признак заблокированной записи */
  public blocked: boolean;

  /** Условия предоставления */
  public impactSettingList: ImpactSettings;
}
