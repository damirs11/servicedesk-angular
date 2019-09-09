/**
 * Данный валидатор проверяет, что значение одного поля равно значению другого поля.
 * Может использоваться, например, на форме смены пароля. Новый пароль надо ввести дважды
 */
function EqualsTo() {
    return {
        require: "ngModel",
        scope: {
            otherModelValue: "=equalsTo"
        },
        link: function (scope, element, attributes, ngModel) {

            ngModel.$validators.equals = function (modelValue) {
                return angular.equals(modelValue, scope.otherModelValue);
            };

            scope.$watch("otherModelValue", function () {
                ngModel.$validate();
            });
        }
    }
}

export {EqualsTo};