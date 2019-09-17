import { Entity } from '../entity/entity';
import { ImpactSettings } from '../impact-settings/impact-settings';

/**
 * Условия предоставления
 */
export class ServiceLevel extends Entity {
  /**
   * Название
   */
  description: string;

  /**
   * Значение по умолчанию (может быть только один среди всех условий)
   */
  defaultValue: boolean;

  /**
   * Признак заблокированной записи
   */
  blocked: boolean;

  /**
   * Условия предоставления
   */
  impactSettingList: ImpactSettings;
}
