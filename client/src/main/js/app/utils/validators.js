/**
 * Валидаторы форм
 */
(function() {
    angular.module('appValidators', [])
        /**
         * Данный валидатор проверяет, что значение одного поля равно значению другого поля.
         * Может использоваться, например, на форме смены пароля.
         */
        .directive("compareTo", function() {
            return {
                require: "ngModel",
                scope: {
                    otherModelValue: "=compareTo"
                },
                link: function(scope, element, attributes, ngModel) {

                    ngModel.$validators.compareTo = function(modelValue) {
                        return angular.equals(modelValue, scope.otherModelValue);
                    };

                    scope.$watch("otherModelValue", function() {
                        ngModel.$validate();
                    });
                }
            }
        });
}());
