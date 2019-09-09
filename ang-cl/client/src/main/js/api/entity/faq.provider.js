import {Parse} from "./decorator/parse.decorator";

FAQProvider.$inject = ["RESTEntity"];
function FAQProvider(RESTEntity) {
    /**
     * База известных ошибок
     * @class
     * @name SD.FAQ
     * @extends SD.RESTEntity
     */
    return class FAQ extends RESTEntity {

        /**
         * Название
         * @property
         * @name SD.FAQ#name
         * @type {string}
         */
        @Parse( String ) name;

        @Parse( String ) entityType;

        toString(){
            return this.name;
        }
    };
}

export {FAQProvider};