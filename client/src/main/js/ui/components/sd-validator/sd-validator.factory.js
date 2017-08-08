SDValidatorFactory.$inject = ["$injector"];
function SDValidatorFactory($injector) {

    function SDValidator(container,validatorName,...validators) {
        for (const i in validators) {
            const validator = validators[i];
            if (typeof validator === "function") continue;
            validators[i] = parseValidator(validator)
        }

        return async function () {
            for (const validator of validators) {
                const result = validator(...arguments);
                if (result) {
                    container[validatorName] = result;
                    return result;
                }
            }
            delete container[validatorName];
            return undefined;
        }
    }
}

function parseValidator(data) {

    return () => {}
}

export {SDValidatorFactory}