import {Parse} from "./decorator/parse.decorator";
import {Nullable} from "./decorator/parse-utils";

UserProvider.$inject = ["RESTEntity", "SD"];
function UserProvider(RESTEntity, SD) {
    /**
     * Пользователь
     * @class
     * @extends SD.RESTEntity
     * @name SD.User
     */
    return class User extends RESTEntity {
        /**
         * Имя пользователя
         * @property
         * @name SD.User#name
         * @type {string}
         */
        @Parse( Nullable(String) ) name;

        /**
         * Логин
         * @property
         * @name SD.User#login
         * @type {string}
         */
        @Parse( String ) login;

        /**
         * Роли
         * @property
         * @name SD.User#roles
         * @type {[Object]}
         */
        @Parse( Object ) roles;

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