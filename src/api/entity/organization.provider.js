import {Parse} from "./decorator/parse.decorator";
import {Nullable} from "./decorator/parse-utils";
import {Serialize} from "./decorator/serialize.decorator";
import {serializeId} from "./decorator/serialize-utils";

OrganizationProvider.$inject = ["RESTEntity", "SD"];
function OrganizationProvider(RESTEntity, SD) {
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

        /**
         * Папка
         * @property
         * @name SD.Organization#folder
         * @type {SD.Person}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.Folder.parse(data)) folder;

        toString(){
            return this.name;
        }
    };
}

export {OrganizationProvider};