/**
 * Валидаторы форм
 */
(function() {
    angular.module('appValidators', [])
        /**
         * Данный валидатор проверяет, что значение одного поля равно значению другого поля.
         * Может использоваться, например, на форме смены пароля. Новый пароль надо ввести дважды
         */
        .directive("equalsTo", function() {
            return {
                require: "ngModel",
                scope: {
                    otherModelValue: "=equalsTo"
                },
                link: function(scope, element, attributes, ngModel) {

                    ngModel.$validators.equals = function(modelValue) {
                        return angular.equals(modelValue, scope.otherModelValue);
                    };

                    scope.$watch("otherModelValue", function() {
                        ngModel.$validate();
                    });
                }
            }
        })
    /**
     * Данный валидатор проверяет, что значение одного поля не совпадает со значением другого поля.
     * Может использоваться, например, на форме смены пароля. Старый и новый пароли должны различаться
     */
    .directive("differentFrom", function() {
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
    });
}());
