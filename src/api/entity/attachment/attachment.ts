import { Entity } from "../entity/entity";
import { FILETYPE_MAP } from "src/api/util/filetype-list";
import { Person } from "../person/person";

/**
 * Для загрузки файлов на сервер используется FileInfo
 * @see FileInfo
 */
export class Attachment extends Entity {
  static get entityType() {
    // Название на сервере.
    return "FileInfo";
  }
  /**
   * Размер вложения в байтах
   * @property
   * @type {Number}
   */
  size: number;
  /**
   * Персона, создавшая вложение
   * @property
   * @type {Person}
   */
  author: Person;
  /**
   * Дата прикрепления
   * @property
   * @type {Person}
   */
  creationDate: Person;

  /**
   * Расширение вложение
   */
  get extension(): string | undefined {
    const index = this.name.lastIndexOf(".");
    if (index < 0) {
      return;
    }
    return this.name.substr(index + 1).toLowerCase();
  }

  /**
   * Тип вложения
   */
  get fileType(): string {
    const fileExt = this.extension;
    if (!fileExt || FILETYPE_MAP[fileExt] == null) {
      return "unknown";
    }
    return FILETYPE_MAP[fileExt];
  }
}
