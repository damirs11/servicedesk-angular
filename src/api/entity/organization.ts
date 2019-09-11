import {Nullable} from "./decorator/parse-utils";
import {Serialize} from "./decorator/serialize.decorator";
import {serializeId} from "./decorator/serialize-utils";

/**
 * Организация
 * @class
 * @name SD.Organization
 */
export class Organization {
    /**
     * Название
     * @property
     * @name SD.Organization#name
     * @type {string}
     */
    name: string;

    /**
     * Почта
     * @property
     * @name SD.Organization#email
     * @type {string|null}
     */
    email: string | null;

    /**
     * Папка
     * @property
     * @name SD.Organization#folder
     * @type {SD.Person}
     */
    @Serialize(Nullable(serializeId)) folder;

    toString() {
        return this.name;
    }
}
