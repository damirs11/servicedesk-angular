const NGInjectSymbol = Symbol("NGInject");

/**
 * Декоратор для внедрения ангуляр-зависимостей в класс
 * @param dependencyName - название зависимости. Если не указывать - будет совпадать с названием поля
 * ВАЖНО! Класс должен быть помечен декоратором @NGInjectClass
 * @see NGInjectClass
 *
 * Пример использования
 * @NGInjectClass
 * class MainPageController {
 *      @NGInject("$user") user
 *      @NGInject() $state;
 *      $onInit(){
 *          if (this.$user.authorized) this.$state.go("app.authorizedPage")
 *      }
 * }
 */
function NGInject(dependencyName) {
        return function decorateNGInject(prototype, propertyName, descriptor) {
            if (!dependencyName) dependencyName = propertyName;
            const map = prototype[NGInjectSymbol] = prototype[NGInjectSymbol] || Object.create(null);
            map[dependencyName] = undefined;
            return {
                enumerable: false, // Ангуляровские зависимости не будут видны в перечислении полей
                get: () => map[dependencyName], // Геттер из скрытой мапы прямо внутри объекта
                set: () => {throw `cannot rewrite NGInject-property ${propertyName} of ${prototype.constructor}`}
            }
        }
}

/**
 * Декоратор для подготовки класса к @NGInject.
 * Подменяет класс на функцию-конструктор, которая прокинет себе ng-заивисмости и вернет класс-исходник
 */
function NGInjectClass() {
    return function (clazz) {
        const prototype = clazz.prototype;
        const map = prototype[NGInjectSymbol];
        if (!map) return;
        const dependencies = Object.keys(map);

        function NGDecoratedClass() {
            for (let i = 0; i < dependencies.length; i++) {
                map[dependencies[i]] = arguments[i];
            }
            return new clazz();
        }
        NGDecoratedClass.$inject = dependencies;

        return NGDecoratedClass
    }
}

export {NGInject, NGInjectClass}