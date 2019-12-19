SDFocusDirective.$inject = ["$parse"];

/**
 * Навешивает фокус на элемент по условию
 */
function SDFocusDirective($parse) {

    const conf = {
        restrict: "A",
        scope: false,
    };

    conf.link = function ($scope, $element, $attrs) {
        let focusedFirstTime = false;

        const focusElement = () => {
            setTimeout(() => {
                $scope.$apply(function(){
                    $element[0].focus()
                })
            },0)
        };

        if (!$attrs.sdFocus) { // Если нет условия - просто вешаем фокус.
            focusElement();
            return;
        }

        const conditionGetter = $parse($attrs.sdFocus).bind(null,$scope.$parent); // Bound to parent scope
        $scope.$watch( conditionGetter, (newValue,oldValue) => {
            if (!focusedFirstTime && newValue) {
                focusedFirstTime = true;
                focusElement();
                return
            }
            if ( newValue && !oldValue ) {
                focusElement()
            }
        } )
    };

    return conf;
}

export {SDFocusDirective}