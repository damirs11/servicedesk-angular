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
   * @property
   * @type {boolean}
   */
  defaultValue: boolean;

  /**
   * Признак заблокированной записи
   * @property
   * @type {boolean}
   */
  blocked: boolean;

  /**
   * Условия предоставления
   * @property
   * @type {ImpactSettings}
   */
  impactSettingList: ImpactSettings;
}
