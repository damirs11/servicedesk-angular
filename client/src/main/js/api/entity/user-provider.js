UserProvider.$inject = ["Entity","SD"];
function UserProvider(Entity,SD) {
    /**
     * Пользователь
     */
    return class User extends Entity {
        /**
         * Имя пользователя
         * @property
         * @name SD.User#name
         * @type {string}
         */
        /**
         * Логин
         * @property
         * @name SD.User#login
         * @type {string}
         */
        /**
         * Роли
         * @property
         * @name SD.User#roles
         * @type {[Object]}
         */
        /**
         * Персона
         * @property
         * @name person
         * @type {object}
         */


        ["parse:name"](value) {
            return value;
        }

        ["parse:login"](value) {
            return value;
        }

        ["parse:roles"](value) {
            return value; // ToDo парси роли
        }

        ["parse:person"](value) {
            return value && SD.Person.parse(value);
        }


    };
}

export {UserProvider};