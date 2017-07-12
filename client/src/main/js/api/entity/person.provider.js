import {Parse} from "./decorator/parse.decorator";


PersonProvider.$inject = ["Entity", "SD","$connector"];
function PersonProvider(Entity, SD, $connector) {
    /**
     * Персона
     * @class
     * @name SD.Person
     */
    return class Person extends Entity {
        /**
         * Пол персоны: 0 - женский, 1 - мужской
         * @property
         * @name SD.Person#sex
         * @type {number}
         */
        @Parse(Number) sex;
        /**
         * Почта
         * @property
         * @name SD.Person#email
         * @type {string}
         */
        @Parse(String) email;
        /**
         * Должность
         * @property
         * @name SD.Person#job
         * @type {string}
         */
        @Parse(String) job;
        /**
         * Имя
         * @property
         * @name SD.Person#firstName
         * @type {string}
         */
        @Parse(String) firstName;
        /**
         * Фамилия
         * @property
         * @name SD.Person#lastName
         * @type {string}
         */
        @Parse(String) lastName;
        /**
         * Отчество
         * @property
         * @name SD.Person#middleName
         * @type {string}
         */
        @Parse(String) middleName;
        /**
         * Организация персоны
         * @property
         * @name SD.Person#organization
         * @type {SD.Organization}
         */
        @Parse(data => SD.Organization.parse(data)) organization;

        /**
         * Сокращенное имя персоны
         * Фамилия + инициалы
         * @property
         * @name SD.Person#shortName
         * @type {String}
         */
        get shortName(){
            return this.lastName
                + this.firstName ? " " + this.firstName[0] + "." : ""
                + this.middleName ? " " + this.middleName[0] + "." : ""
        }

        /**
         * Полное имя персоны
         * Фамилия + имя + отчество
         * @property
         * @name SD.Person#shortName
         * @type {String}
         */
        get fullName(){
            return [ this.lastName, this.firstName, this.middleName ]
                .filter(_=>_)
                .join(' ')
            ;
        }

        async load(){
            const data = await $connector.get(`rest/entity/Person/${this.id}`);
            return this.$update(data);
        }
    };
}

export {PersonProvider};