import { Entity } from '../entity/entity';
import { ImpactSettings } from '../impact-settings/impact-settings';

/**
 * Условия предоставления
 * @class
 * @name ServiceLevel
 */
export class ServiceLevel extends Entity {
  /**
   * Название
   * @property
   * @name ServiceLevel#description
   * @type {string}
   */
  description: string;

  /**
   * Значение по умолчанию (может быть только один среди всех условий)
   * @property
   * @name ServiceLevel#defaultValue
   * @type {boolean}
   */
  defaultValue: boolean;

  /**
   * Признак заблокированной записи
   * @property
   * @name ServiceLevel#blocked
   * @type {boolean}
   */
  blocked: boolean;

  /**
   * Условия предоставления
   * @property
   * @name ServiceLevel#impactSettingList
   * @type {ImpactSettings}
   */
  impactSettingList: ImpactSettings;
}
