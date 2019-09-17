import { Entity } from "../entity/entity";
import { Organization } from '../organization/organization';
import { EntityCategory } from '../entity-category/entity-category';

/**
 * Персона
 */
export class Person extends Entity {
  /**
   * Пол персоны: true(1) - женский, false(0) - мужской
   * @property
   * @type {boolean}
   */
  sex: boolean;
  /**
   * Почта
   * @property
   * @type {string}
   */
  email: string;
  /**
   * Должность
   * @property
   * @type {string}
   */
  job: string;
  /**
   * Имя
   * @property
   * @type {string}
   */
  firstName: string;
  /**
   * Фамилия
   * @property
   * @type {string}
   */
  lastName: string;
  /**
   * Отчество
   * @property
   * @type {string}
   */
  middleName: string;
  /**
   * Сокращенное имя персоны
   * Фамилия + инициалы
   * @property
   * @type {String}
   */
  shortName: string;
  /**
   * Организация персоны
   * @property
   * @type {Organization}
   */
  organization: Organization;

  /**
   * Категория
   * @property
   * @type {EntityCategory}
   */
  category: EntityCategory;

  /**
   * Полное имя персоны
   * Фамилия + имя + отчество
   * @property
   * @type {String}
   */
  get fullName() {
    return [this.lastName, this.firstName, this.middleName]
      .filter(_ => _)
      .join(" ");
  }

  /**
   * Override
   */
  toString(): string {
    return this.fullName;
  }
}
