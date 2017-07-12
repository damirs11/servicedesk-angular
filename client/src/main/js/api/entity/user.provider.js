import {Parse} from "./decorator/parse.decorator";

UserProvider.$inject = ["Entity", "SD"];
function UserProvider(Entity, SD) {
    /**
     * Пользователь
     * @class
     * @name SD.User
     */
    return class User extends Entity {
        /**
         * Имя пользователя
         * @property
         * @name SD.User#name
         * @type {string}
         */
        @Parse(String) name;

        /**
         * Логин
         * @property
         * @name SD.User#login
         * @type {string}
         */
        @Parse(String) login;

        /**
         * Роли
         * @property
         * @name SD.User#roles
         * @type {[Object]}
         */
        @Parse(Object) roles;

        /**
         * Персона
         * @property
         * @name person
         * @type {object}
         */
        @Parse(data => SD.Person.parse(data)) person;

        toString(){
            return this.name
        }

    };
}

export {UserProvider};