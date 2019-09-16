import template from "./sd-loading.html"
import {controller} from "./sd-loading.ctrl"

SDLoadingDirective.$inject = ["$parse", "$timeout"];

/**
 * Директива-атрибут, для отображения анимации загрузки.
 * Использование:
 * <sd-loading="ctrl.isLoadingData" sd-loading-size="13">
 *      <content> Контент </content>
 *      <spinner> ... loading </spinner>
 *      <error> ... возникла ошибка </error>
 * </div>
 * Если значение выражение sd-loading true - отображается иконка загрузки.
 * Если false - отображается контент.
 * Параметры:
 * sd-loading {Promise} - промис. Пока он в режиме pending - будет отображаться spinner,
 * при резолве - content, при ошибке - error
 * sd-loading-size {number} - размер шрифта для иконки загрузки
 * Параметры трансклуда
 * content - сам контент, отобразится когда промис резолвнется.
 * spinner - Необязательный. Иконка загрузки, отображается пока промис в статусе pending
 * error - Необязательный. Отображение ошибки.
 */
function SDLoadingDirective($parse, $timeout) {
    return  {
        restrict: "EA",
        scope: {},
        transclude: {
            spinner: "?spinner",
            content: "content",
            error: "?error",
        },
        controller: controller,
        controllerAs: "ctrl",
        bindToController : {
            sdLoadingSize: "<",
            sdLoading: "<"
        },
        template: template,
    };
}

export {SDLoadingDirective}