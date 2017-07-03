/**
 * Директива, проверяющая, отличается ли значение текущего элемента от заданного в differentFrom=
 */
export function DifferentFrom() {
    return {
        require: "ngModel",
        scope: {
            otherModelValue: "=differentFrom"
        },
        link: function(scope, element, attributes, ngModel) {

            ngModel.$validators.different = function(modelValue) {
                return !angular.equals(modelValue, scope.otherModelValue);
            };

            scope.$watch("otherModelValue", function() {
                ngModel.$validate();
            });
        }
    }
}