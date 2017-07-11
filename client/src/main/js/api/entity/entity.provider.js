import {PARSE_MAP} from "./decorator/parse.decorator";

function EntityProvider() {
    /**
     * Базовый класс для сущностей ServiceDesk
     * Содержит служебную логику, например кэш.
     *
     * //todo Саша: описать для чего класс используется (служебная логика), для чего в нем кэш? Неплохое документирование методов, но в целом картина не складывается
     * // Ок, как закончу с entity до конца - опишу все в документации.
     */
    return class Entity {

        /**
         * Кэш объектов. Необходим, чтобы при обновлении какого-либо объекта не осталось его копий
         * со старыми данными.
         */
        static cache = {}; // Закэшированные сущности


        /**
         * Превращает json данные в SD сущность.
         * Если был передан трекер - вернет сущность связанную с кешем.
         * Если необходимо трекерить объект не по ID, следует переопределить данный метод.
         * @param {object|number} data - json данные или трекер.
         */
        static parse(data) {
            if (!data) return null;
            if (typeof data !== "object") return new this(data);
            return new this(data.id).$update(data)
        }

        /**
         * Создает сущность. Если не передать tracker - считается, что создается новая сущность (которой нет в БД)
         * Она не попадет в кэш.
         * Если же tracker передан - вернет новый объект, который наследуется от кэшированного.
         * @param {number|string} [tracker] - трекер сущности
         * @returns {Entity|*}
         */
        constructor(tracker) {
            if (tracker) {
                let entity = Entity.cache[tracker];
                if (!entity) {
                    Entity.cache[tracker] = entity = Object.create(this.constructor.prototype);
                }
                Object.defineProperty(entity, "$data", {value: entity});
                return Object.create(entity)
            }
            const data = Object.create(this.constructor.prototype);
            Object.defineProperty(data, "$data", {value: data});
            return Object.create(data);
        }

        /**
         * Обновить объект в кэше.
         * Для каждого ключа из пришедших данных ищет у кэшированного объекта
         * поле parse:%key% и вызывает его, передвая туда значение data[%key%], сам объект data, ключ и
         * закэшированный объект. Если метод вернул что-либо не undefined - заносит это в поле cachedObject[%key%]
         * @param {object} data - json данные
         */
        $update(data) {
            const cached = this.$data;
            const parseData = Object.create(null);
            let obj = cached;
            while (obj = Object.getPrototypeOf(obj)) {
                const map = obj[PARSE_MAP];
                if (!map) continue;
                for (const val in map) {
                    parseData[val] = parseData[val] || map[val];
                }
            }
            for (const key in data) {
                const value = data[key];
                const mapDescriptor = parseData[key];
                if (!mapDescriptor) continue;
                const parse = mapDescriptor.parse;
                const result = parse.call(cached,value,data,key,cached);
                if (result !== undefined) cached[mapDescriptor.propertyName] = result;
            }
            return this;
        }

    }
}

export {EntityProvider};