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
         * Возвращает сущность из данных json. Может изменить кэшированную версию.
         * Если был передан id - вернет сущность связанную с кешем.
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
         * Если же id передан - вернет новый объект, который наследуется от кэшированного.
         * @param {number} [id] - ID сущности
         * @returns {Entity|*}
         */
        constructor(id) {
            if (id) {
                let entity = Entity.cache[id];
                if (!entity) {
                    Entity.cache[id] = entity = Object.create(this.constructor.prototype);
                    entity.id = id;
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
         * Кэш обновляется с помощью методов, которые добавляет @Parse
         * @see @Parse
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