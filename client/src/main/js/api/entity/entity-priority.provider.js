import {Parse} from "./decorator/parse.decorator";

PriorityProvider.$inject = ["Entity"];
function PriorityProvider(Entity) {
    /**
     * Приоритет
     * @class
     * @name SD.EntityPriority
     * @extends SD.Entity
     */
    return class EntityPriority extends Entity {

        /**
         * Название
         * @property
         * @name SD.EntityPriority#name
         * @type {string}
         */
        @Parse( String ) name;

        toString(){
            return this.name
        }

        /**
         * Возвращает стиль отображения
         * @returns {string}
         */
        getStyle() {
            switch(this.name) {
                case "Высший": {
                    return {color: "#FF3328", class: "fa fa-exclamation"};
                }
                case "Высокий": {
                    return {color: "#FF8000", class: "fa fa-arrow-up"};
                }
                case "Средний": {
                    return {color: "#00A000", class: "fa fa-chevron-up"};
                }
                case "Низкий": {
                    return {color: "#66B0E8", class: "fa fa-chevron-down"};
                }
                default: {
                    return {color: "#99A0A3", class: "fa fa-arrow-down"};
                }
            }
        }
    };
}

export {PriorityProvider};