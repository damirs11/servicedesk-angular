import {Parse} from "./decorator/parse.decorator";
import {Nullable} from "./decorator/parse-utils";

OrganizationProvider.$inject = ["RESTEntity"];
function OrganizationProvider(RESTEntity) {
    /**
     * Организация
     * @class
     * @name SD.Organization
     */
    return class Organization extends RESTEntity {
        /**
         * Название
         * @property
         * @name SD.Organization#name
         * @type {string}
         */
        @Parse( String ) name;

        /**
         * Почта
         * @property
         * @name SD.Organization#email
         * @type {string|null}
         */
        @Parse( Nullable(String) ) email;

        toString(){
            return this.name;
        }
    };
}

export {OrganizationProvider};