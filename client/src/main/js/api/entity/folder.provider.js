import {Parse} from "./decorator/parse.decorator";

FolderProvider.$inject = ["RESTEntity"];
function FolderProvider(RESTEntity) {
    /**
     * Папка
     * @class
     * @name SD.Folder
     * @extends SD.RESTEntity
     */
    return class Folder extends RESTEntity {

        /**
         * Название
         * @property
         * @name SD.Folder#name
         * @type {string}
         */
        @Parse( String ) name;

        @Parse( String ) entityType;

        toString(){
            return this.name
        }
    };
}

export {FolderProvider};