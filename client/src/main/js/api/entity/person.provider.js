import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Nullable} from "./decorator/parse-utils";


PersonProvider.$inject = ["EditableEntity", "SD","$connector"];
function PersonProvider(EditableEntity, SD, $connector) {
    /**
     * Персона
     * @class
     * @name SD.Person
     */
    return class Person extends EditableEntity {
        /**
         * Пол персоны: false - женский, true - мужской
         * @property
         * @name SD.Person#sex
         * @type {boolean}
         */
        @Serialize(Boolean) @Parse( Nullable(Boolean) ) sex;
        /**
         * Почта
         * @property
         * @name SD.Person#email
         * @type {string}
         */
        @Serialize(String) @Parse( Nullable(String) ) email;
        /**
         * Должность
         * @property
         * @name SD.Person#job
         * @type {string}
         */
        @Serialize(String) @Parse( Nullable(String) ) job;
        /**
         * Имя
         * @property
         * @name SD.Person#firstName
         * @type {string}
         */
        @Serialize(String) @Parse( Nullable(String) ) firstName;
        /**
         * Фамилия
         * @property
         * @name SD.Person#lastName
         * @type {string}
         */
        @Serialize(String) @Parse( Nullable(String) ) lastName;
        /**
         * Отчество
         * @property
         * @name SD.Person#middleName
         * @type {string}
         */
        @Serialize(String) @Parse( Nullable(String) ) middleName;
        /**
         * Организация персоны
         * @property
         * @name SD.Person#organization
         * @type {SD.Organization}
         */
        @Serialize(org => org.id) @Parse(data => SD.Organization.parse(data)) organization;

        /**
         * Сокращенное имя персоны
         * Фамилия + инициалы
         * @property
         * @name SD.Person#shortName
         * @type {String}
         */
        get shortName(){
            return this.lastName +
                (this.firstName ? " " + this.firstName[0] + "." : "") +
                (this.middleName ? " " + this.middleName[0] + "." : "")
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

        toString(){
            return this.fullName
        }
    };
}

export {PersonProvider};