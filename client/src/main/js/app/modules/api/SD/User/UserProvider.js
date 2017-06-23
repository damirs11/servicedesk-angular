UserProvider.$inject = ["SD","Entity"];
export function UserProvider(SD,Entity){
    /**
     * Пользователь
     */
    return class User extends Entity{

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


        ["parse:name"](value){ return value; }
        ["parse:login"](value){ return value; }
        ["parse:roles"](value){ return value; }
        ["parse:person"](value){ return value; }

    };
}