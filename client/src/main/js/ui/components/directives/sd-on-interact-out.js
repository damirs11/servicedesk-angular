import {jQuery as $} from "../../../common/web-libraries.const"

SDOnInteractOutDirective.$inject = ["$parse"];
/**
 * Навешивает фокус на элемент по условию
 * использование:
 * <div sd-on-interact-out="action()" sd-on-interact-out-if="condition">
 *
 * </div>
 * action - действие, которое выполнится, когда пользователь нажмет вне элемента
 * condition - условие, идет прослушиваение
 */
function SDOnInteractOutDirective($parse) {

    const conf = {
        restrict: "A",
        scope: true,
    };

    conf.link = function ($scope, $element, $attrs) {
        const action = $parse($attrs['sdOnInteractOut']).bind(null,$scope.$parent); // Bound to parent scope
        const condition = $parse($attrs['sdOnInteractOutIf']).bind(null,$scope.$parent); // Bound to parent scope
        const listener = event => {
            const target = $(event.target);
            if ($element.is(target)) return;
            if ($.contains($element[0],target[0])) return;
            $scope.$apply(() => {
                action({$event: event})
            })
        };

        const body = window.document.body;
        if ($attrs['sdOnInteractOutIf']) {
            $scope.$watch(condition, (newValue, oldValue) => {
                if ( Boolean(newValue) == Boolean(oldValue) ) return;
                if ( Boolean(newValue) ) {
                    body.addEventListener("mousedown",listener)
                } else {
                    body.removeEventListener("mousedown", listener)
                }
            })
        } else {
            body.addEventListener("mousedown",listener)
        }

        $scope.$on("$destroy", () => {
            body.removeEventListener("mousedown", listener)
        })
    };

    return conf;
}

export {SDOnInteractOutDirective}