const PARSE_MAP = Symbol("Parse:PARSE_MAP");

/**
 * Аннотация. Используется у SD.Entity для внесения данных из json внутрь объекта
 * ` defaultValue
 * ` field
 * ` field
 * ` field
 * dataFieldName - название поля в json данных, приходящих с сервера
 * mapperFunction - функция обработки данных перед внесением в объект
 * @param {(string|Function)} parseParams
 * @returns {Function}
 */
function Parse(...parseParams) {
    const parse = parseParams.find(arg => typeof arg === "function") || (_=>_);
    const dataName = parseParams.find(arg => typeof arg === "string");

    return function decorateParse(prototype, propertyName, descriptor) {
        const name = dataName || propertyName;
        
        // карта используется для сопоставления полей json с полями класса
        const map = prototype[PARSE_MAP] = prototype[PARSE_MAP] || Object.create(null);
        map[name] = {propertyName, parse};

        const initializer = descriptor.initializer;

        descriptor.value = initializer ? initializer.call(prototype) : undefined;
        delete descriptor.initializer;

        descriptor.writable = true;
        return descriptor;
    }
}

export {PARSE_MAP,Parse};