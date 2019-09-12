const SERIALIZE_MAP = Symbol("Serialize:SERIALIZE_MAP");


/**
 * Аннотация. Используется у SD.Entity для внесения данных из json внутрь объекта
 * `  field = defaultValue
 * `  field
 * `  field
 * `  field
 * jsonFieldName - название, под которым будет лежать значение этого поля в json
 * serializeFunction - функция сериализации
 * Если serializeFunction вернет undefined в json ничего не занесется.
 * @param {(string|Function)} serializeParams
 * @returns {Function}
 */
function Serialize(...serializeParams) {
    const serialize = serializeParams.find(arg => typeof arg === "function") || (_=>_);
    const dataName = serializeParams.find(arg => typeof arg === "string");

    return function decorateParse(prototype, propertyName, descriptor) {
        const serializedName = dataName || propertyName;
        const map = prototype[SERIALIZE_MAP] = prototype[SERIALIZE_MAP] || Object.create(null);
        map[propertyName] = {serializedName, serialize};
        const initializer = descriptor.initializer;
        descriptor.value = initializer ? initializer.call(prototype) : undefined;
        delete descriptor.initializer;
        descriptor.writable = true;
        return descriptor;
    }
}

export {Serialize,SERIALIZE_MAP};