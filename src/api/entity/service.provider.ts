import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {Nullable} from "./decorator/parse-utils";
import {serializeId} from "./decorator/serialize-utils";

/**
 * Сервис/услуга
 * @class
 * @name SD.Service
 */
class Service {

    /**
     * Название
     * @property
     * @name SD.Service#name
     * @type {string}
     */
    @Parse(String) name: string;

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