AutoHeightDirective.$inject = [];
function AutoHeightDirective() {

    return function (scope, element, { hMax, hMin, autoHeight }) {
        var nElement = element[0];

        function recalculateHeight() {
            element.css({ 'height': '0', 'overflow': 'hidden' });

            var height = nElement.scrollHeight + 2;
            var overscroll = false;

            if (height > hMax) {
                overscroll = true;
                height = +hMax;
            }

            if (height < hMin)
                height = +hMin;

            element.css({ 'height': height });

            if (overscroll)
                element.css({ 'overflow': '' });

            console.log("recalculated, ", height);
        }

        function delayedRecalculate(newValue) {
            newValue && setTimeout(recalculateHeight, 4);
        }


        element.on('input', recalculateHeight);
        // scope.$evalAsync(recalculateHeight);
        scope.$watch(autoHeight, delayedRecalculate);

        scope.$on('$destroy', () => {
            element.off('input', recalculateHeight);
        });
    }
}


export { AutoHeightDirective };
