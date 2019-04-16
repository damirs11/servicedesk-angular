import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Nullable} from "./decorator/parse-utils";
import {serializeId} from "./decorator/serialize-utils";

ServiceProvider.$inject = ["SD", "RESTEntity"];
function ServiceProvider(SD, RESTEntity) {
    /**
     * Сервис/услуга
     * @class
     * @name SD.Service
     */
    class Service extends RESTEntity {

        /**
         * Название
         * @property
         * @name SD.Service#name
         * @type {string}
         */
        @Parse(String) name;

        /**
         * Статус
         * @property
         * @name SD.Service#status
         * @type {SD.EntityStatus}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityStatus.parse(data)) status;

        /**
         * Папка
         * @property
         * @name SD.Service#folder
         * @type {SD.Folder}
         */
        @Serialize(Nullable(serializeId)) @Parse(data => SD.Folder.parse(data)) folder;
        
        toString(){
            return String(this.name);
        }

    }
    return Service;
}

export {ServiceProvider};