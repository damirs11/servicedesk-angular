import { Entity } from "../entity/entity";
import { Organization } from '../organization/organization';
import { EntityCategory } from '../entity-category/entity-category';

/**
 * Персона
 * @class
 * @name Person
 * @mixes ENTITY_MIXIN.AttachmentsHolder
 */
export class Person extends Entity {
  /**
   * Пол персоны: true(1) - женский, false(0) - мужской
   * @property
   * @name Person#sex
   * @type {boolean}
   */
  sex: boolean;
  /**
   * Почта
   * @property
   * @name Person#email
   * @type {string}
   */
  email: string;
  /**
   * Должность
   * @property
   * @name Person#job
   * @type {string}
   */
  job: string;
  /**
   * Имя
   * @property
   * @name Person#firstName
   * @type {string}
   */
  firstName: string;
  /**
   * Фамилия
   * @property
   * @name Person#lastName
   * @type {string}
   */
  lastName: string;
  /**
   * Отчество
   * @property
   * @name Person#middleName
   * @type {string}
   */
  middleName: string;
  /**
   * Сокращенное имя персоны
   * Фамилия + инициалы
   * @property
   * @name Person#shortName
   * @type {String}
   */
  shortName: string;
  /**
   * Организация персоны
   * @property
   * @name Person#organization
   * @type {Organization}
   */
  organization: Organization;

  /**
   * Категория
   * @property
   * @name Person#category
   * @type {EntityCategory}
   */
  category: EntityCategory;

  /**
   * Полное имя персоны
   * Фамилия + имя + отчество
   * @property
   * @name Person#shortName
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
