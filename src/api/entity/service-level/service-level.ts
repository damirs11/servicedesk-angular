import { Entity } from '../entity/entity';
import { ImpactSettings } from '../impact-settings/impact-settings';

/**
 * Условия предоставления
 */
export class ServiceLevel extends Entity {
  /**
   * Название
   * @property
   * @type {string}
   */
  description: string;

  /**
   * Значение по умолчанию (может быть только один среди всех условий)
   * @type {boolean}
   */
  defaultValue: boolean;

  /**
   * Признак заблокированной записи
   * @type {boolean}
   */
  blocked: boolean;

  /**
   * Условия предоставления
   * @type {ImpactSettings}
   */
  impactSettingList: ImpactSettings;
}
