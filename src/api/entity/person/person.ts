import { Entity } from "../entity/entity";
import { Organization } from '../organization/organization';
import { EntityCategory } from '../entity-category/entity-category';

/**
 * Персона
 */
export class Person extends Entity {
  /**
   * Пол персоны: true(1) - женский, false(0) - мужской
   */
  sex: boolean;
  /**
   * Почта
   */
  email: string;
  /**
   * Должность
   */
  job: string;
  /**
   * Имя
   */
  firstName: string;
  /**
   * Фамилия
   */
  lastName: string;
  /**
   * Отчество
   */
  middleName: string;
  /**
   * Сокращенное имя персоны
   * Фамилия + инициалы
   */
  shortName: string;
  /**
   * Организация персоны
   */
  organization: Organization;

  /**
   * Категория
   */
  category: EntityCategory;

  /**
   * Полное имя персоны
   * Фамилия + имя + отчество
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
