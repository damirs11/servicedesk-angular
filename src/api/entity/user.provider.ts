/**
 * Пользователь
 * @class
 * @extends SD.RESTEntity
 * @name SD.User
 */
export class User {
    /**
     * Имя пользователя
     * @property
     * @name SD.User#name
     * @type {string}
     */
    name: string;

    /**
     * Логин
     * @property
     * @name SD.User#login
     * @type {string}
     */
    login: string;

    /**
     * Роли
     * @property
     * @name SD.User#roles
     * @type {[Object]}
     */
    roles: [Object];

    /**
     * Персона
     * @property
     * @name person
     * @type {object}
     */
    person;

    toString() {
        return this.name;
    }
}
