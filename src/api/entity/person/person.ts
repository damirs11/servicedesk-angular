import { Entity } from "../entity/entity";
import { Organization } from '../organization/organization';
import { EntityCategory } from '../entity-category/entity-category';

/** Персона */
export class Person extends Entity {
  /** Пол персоны: true(1) - женский, false(0) - мужской */
  public sex: boolean;
  /** Почта */
  public email: string;
  /** Должность */
  public job: string;
  /** Имя */
  public firstName: string;
  /** Фамилия */
  public lastName: string;
  /** Отчество */
  public middleName: string;
  /**
   * Сокращенное имя персоны
   * Фамилия + инициалы
   */
  public shortName: string;
  /** Организация персоны */
  public organization: Organization;

  /** Категория */
  public category: EntityCategory;

  /**
   * Полное имя персоны
   * Фамилия + имя + отчество
   */
  get fullName() {
    return [this.lastName, this.firstName, this.middleName]
      .filter(_ => _)
      .join(" ");
  }

  /** @Override */
  toString(): string {
    return this.fullName;
  }
}
