import {AbstractGridProvider} from "./abstract-grid.provider";
import {ChangeGridProvider} from "./change-grid.provider";

$gridFactory.$inject = ["$injector"];
function $gridFactory($injector) {
    /**
     * Объект, предоставляющий классы таблиц
     * @type {$grid}
     */
    const $grid = $injector.instantiate($gridConstructor);
    Object.freeze($grid);

    return $grid
}

/**
 * Класс, предоставляющий доступ к классам таблиц
 * @constructor
 * @name $grid
 */
const $gridConstructor = function ($injector) {

    const AbstractGrid = $injector.instantiate(AbstractGridProvider);
    const locales = {AbstractGrid,$grid:this};

    this.ChangeGrid = $injector.instantiate(ChangeGridProvider,locales);

};
$gridConstructor.$inject = ["$injector"];

export {$gridFactory}