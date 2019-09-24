import template from "./sd-entity-error.html"

/**
 * Директива-атрибут, для отображения ошибки внутри страницы сущности
 * Использование:
 * <sd-entity-error>
 *      <? class="entity-error__header__text"> Заголовок </header>
 *      <? class="entity-error__body__text"> Сообщение </>
 * </sd-entity-error>
 * Параметры трансклуда
 * .entity-error__header__text - заголовок окна ошибки
 * .entity-error__body__text - описание ошибки
 */
function SDEntityErrorDirective() {
    return  {
        restrict: "EA",
        scope:{},
        transclude: {
            header: "header",
            message: "message",
        },
        template: template,
    };
}

export {SDEntityErrorDirective}