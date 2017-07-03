/**
 * Данный валидатор проверяет, что значение одного поля отличается значению другого поля.
 * Например, при проверке того, что новый пароль отличается от старого на форме его смены.
 */
function DifferentFrom() {
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

export {DifferentFrom};