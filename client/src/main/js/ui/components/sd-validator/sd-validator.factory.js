SDValidatorFactory.$inject = ["$injector"];
function SDValidatorFactory($injector) {

    function SDValidator(container,validatorName,validator) {
        if (typeof validator !== "function") {
            validator = parseValidator(validator);
        }

        return function () {
            const result = validator(...arguments);
            if (!result) {
                delete container[validatorName]
            } else {
                container[validatorName] = result;
            }
            return result;
        }
    }
}

function parseValidator(data) {
    return () => {}
}

export {SDValidatorFactory}