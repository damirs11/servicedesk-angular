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
   * @type {string}
   */
  email: string;
  /**
   * Должность
   * @type {string}
   */
  job: string;
  /**
   * Имя
   * @type {string}
   */
  firstName: string;
  /**
   * Фамилия
   * @type {string}
   */
  lastName: string;
  /**
   * Отчество
   * @type {string}
   */
  middleName: string;
  /**
   * Сокращенное имя персоны
   * Фамилия + инициалы
   * @type {String}
   */
  shortName: string;
  /**
   * Организация персоны
   * @type {Organization}
   */
  organization: Organization;

  /**
   * Категория
   * @type {EntityCategory}
   */
  category: EntityCategory;

  /**
   * Полное имя персоны
   * Фамилия + имя + отчество
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
