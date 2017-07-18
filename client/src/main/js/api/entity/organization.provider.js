import {Parse} from "./decorator/parse.decorator";

OrganizationProvider.$inject = ["Entity"];
function OrganizationProvider(Entity) {
    /**
     * Организация
     * @class
     * @name SD.Organization
     */
    return class Organization extends Entity {
        /**
         * Название
         * @property
         * @name SD.Organization#name
         * @type {string}
         */
        @Parse(String) name;

        /**
         * Почта
         * @property
         * @name SD.Organization#email
         * @type {string}
         */
        @Parse(String) email;

        toString(){
            return this.name
        }
    };
}

export {OrganizationProvider};