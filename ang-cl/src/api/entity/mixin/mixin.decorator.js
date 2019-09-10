/**
 * Аннотация. Используется для имплементирования классом
 * ` @Mixin(Historyable)
 * ` class A {...}
 * @param {Function[]} classes
 * @returns {Function}
 */
function Mixin(...classes) {

    return function decorateParse(origin, propertyName, descriptor) {
        // Object.assign(prototype,classes.map(c => c.prototype));
        classes.map(x => x.prototype).forEach(c => {
            // Получаем все поля, кроме конструктора
            const keys = Object.getOwnPropertyNames(c)
                    .filter(propName => propName !== "constructor");
            for (let i = 0; i < keys.length; i++) {
                const key = keys[i];
                origin.prototype[key] = c[key]
            }
        });
        return descriptor;
    }
}

export {Mixin};