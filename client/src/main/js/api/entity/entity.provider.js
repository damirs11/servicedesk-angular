import {PARSE_MAP} from "./decorator/parse.decorator";
import {SERIALIZE_MAP} from "./decorator/serialize.decorator";

EntityProvider.$inject = ["cache"];
function EntityProvider(cache) {
    /**
     * Базовый класс для сущностей ServiceDesk
     * Содержит служебную логику и методы load и list
     * @class
     * @name SD.Entity
     */
    return class Entity {


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
                let entity = cache[id];
                if (!entity) {
                    cache[id] = entity = Object.create(this.constructor.prototype);
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
         * @chain
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

        /**
         * Сериализует объект. Будут включены все поля.
         * Важно! При сериализации undefined поля пропускаются.
         * Если на сервер нужно отослать "пустое поле", в поле нужно занести null.
         * При значение null функции сериалзиации не срабатывают, в json заносится null
         */
        $serialize() {
            const serializeData = Object.create(null);
            let obj = this.$data;
            while (obj = Object.getPrototypeOf(obj)) {
                const map = obj[SERIALIZE_MAP];
                if (!map) continue;
                for (const val in map) {
                    serializeData[val] = serializeData[val] || map[val];
                }
            }
            const json = Object.create(null);
            for (const key in this) {
                const value = this[key];
                if (this[key] === undefined) continue;
                const serializeDescriptor = serializeData[key];
                if (!serializeDescriptor) continue;
                const serialize = serializeDescriptor.serialize;
                const serializedName = serializeDescriptor.serializedName;
                if (this[key] === null) {
                    json[serializedName] = null;
                    continue;
                }
                const result = serialize(value,serializedName);
                if (result !== undefined) json[serializedName] = result;
            }
            return json;
        }

        /**
         * Возвращает объект с измененными полями сущности
         */
        get $modifiedData() {
            const serializeData = Object.create(null);
            let obj = this.$data;
            while (obj = Object.getPrototypeOf(obj)) {
                const map = obj[SERIALIZE_MAP];
                if (!map) continue;
                for (const val in map) {
                    serializeData[val] = serializeData[val] || map[val];
                }
            }
            const json = Object.create(null);
            const fields = Object.keys(this);
            for (let i = 0; i < fields.length; i++) {
                const key = fields[i];
                const value = this[key];
                const serializeDescriptor = serializeData[key];
                if (!serializeDescriptor) continue;
                const serialize = serializeDescriptor.serialize;
                const serializedName = serializeDescriptor.serializedName;
                const result = serialize(value,serializedName);
                if (result !== undefined) json[serializedName] = result;
            }
            return json;
        }


        /**
         * Проверяет, изменялось ли данное поле объекта.
         * Если вызвать без аргументов, проверит изменялся ли объект в целом.
         * @param [field] {string} - название поля
         * @returns {boolean}
         */
        checkModified(field){
            if (!field) {
                return Object.keys(this).length > 0
            } else {
                return Object.keys(this).indexOf(field) > 0
            }
        }


        /**
         * Возвращает объект к состоянию, в котором находится кэш
         * @chain
         */
        reset(){
            Object.keys(this)
                .forEach(key => delete this[key])
            ;
            return this
        }

    }
}

export {EntityProvider};