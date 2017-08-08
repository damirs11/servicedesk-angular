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
         * Определяет цвет пиктограммы
         * @returns {string}
         */
        getColor() {
            switch(this.name) {
                case "Высший": {
                    return "#FF3328";
                }
                case "Высокий": {
                    return "#FF8000";
                }
                case "Средний": {
                    return "#00A000";
                }
                case "Низкий": {
                    return "#66B0E8";
                }
                default: {
                    return "#99A0A3";
                }
            }
        }

        /**
         * Определяет стиль пиктограммы
         * @returns {string}
         */
        getIcon() {
            switch(this.name) {
                case "Высший": {
                    return "fire";
                }
                case "Высокий": {
                    return "forward";
                }
                case "Средний": {
                    return "play";
                }
                case "Низкий": {
                    return "chevron-right";
                }
                default: {
                    return "pause";
                }
            }
        }
    };
}

export {PriorityProvider};