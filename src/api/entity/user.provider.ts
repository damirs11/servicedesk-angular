import {Parse} from "./decorator/parse.decorator";
import {Nullable} from "./decorator/parse-utils";
import {Person} from './person.provider';


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
    @Parse( Nullable(String) ) name: string;

    /**
     * Логин
     * @property
     * @name SD.User#login
     * @type {string}
     */
    @Parse( String ) login: string;

    /**
     * Роли
     * @property
     * @name SD.User#roles
     * @type {[Object]}
     */
    @Parse( Object ) roles: [Object];

    /**
     * Персона
     * @property
     * @name person
     * @type {object}
     */
    @Parse(data => SD.Person.parse(data)) person: Person;

    toString(){
        return this.name
    }

};
