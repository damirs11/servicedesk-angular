import {Parse} from "./decorator/parse.decorator";

EntityCategoryProvider.$inject = ["RESTEntity"];
function EntityCategoryProvider(RESTEntity) {
    /**
     * Классификация
     * @class
     * @name SD.EntityCategory
     * @extends SD.RESTEntity
     */
    return class EntityCategory extends RESTEntity {

        /**
         * Название
         * @property
         * @name SD.EntityCategory#name
         * @type {string}
         */
        @Parse( String ) name;

        @Parse( String ) entityType;

        toString(){
            return this.name
        }
    };
}

export {EntityCategoryProvider};