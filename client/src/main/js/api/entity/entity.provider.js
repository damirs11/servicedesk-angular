import {PARSE_MAP} from "./decorator/parse.decorator";
import {SERIALIZE_MAP} from "./decorator/serialize.decorator";

/**
 * Ключи для того, чтобы функции сериализации могли различить в каком режиме их вызвали
 * FULL - режим полной сериализации, попадают все поля.
 * MODIFIED - только те, что изменились
 * Необходимо для сериализации вложенных объектов.
 */
const SERIALIZE_MODE_FULL = "FULL";
const SERIALIZE_MODE_MODIFIED = "MODIFIED";

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
         * @static
         * @name Entity.constructor#$tracker
         * Объект, содержащий настройки трекера.
         * dataField - названия поля в данных, приходящих с сервера (json / etc.)
         * ownField - название поля в объекте, куда занесется значение трекера.
         * По стандарту оба поля "id"
         */
        static $tracker = {
              dataField: "id",
              ownField: "id"
        };

        /**
         * Идентификатор типа сущности
         * @static
         * @property
         * @name SD.Entity.$entityTypeId
         * @type {Number}
         */
        static $entityTypeId;

        /**
         * Возвращает строкой тип сущности. Используется для запросов
         * Должен соотвествовать типу на сервере
         * @returns {string}
         */
        static get $entityType() {
            return this.name;
        }

        /**
         * Возвращает сущность из данных json. Может изменить кэшированную версию.
         * Если был передан id - вернет сущность связанную с кешем.
         * @param {object|number} data - json данные или трекер.
         */
        static parse(data) {
            if (!data) return null;
            if (typeof data !== "object") return new this(data);
            const serverTrackerName = this.$tracker.dataField;
            return new this(data[serverTrackerName]).$update(data);
        }

        /**
         * Создает сущность. Если не передать tracker - считается, что создается новая сущность (которой нет в БД)
         * Она не попадет в кэш.
         * Если же tracker передан - вернет новый объект, который наследуется от кэшированного.
         * Переданный tracker попадет в поле, указанное в $tracker["ownField"].
         * @link Entity.constructor#$tracker
         * @param {number} [tracker] - трекер сущности
         * @returns {Entity|*}
         */
        constructor(tracker) {
            const trackerField = this.constructor.$tracker.ownField;
            const entType = this.constructor.$entityType;
            if (tracker) {
                if (!cache[entType]) cache[entType] = {};
                let entity = cache[entType][tracker];
                if (!entity) {
                    cache[entType][tracker] = entity = Object.create(this.constructor.prototype);
                    entity[trackerField] = tracker;
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
         * @see Parse
         * @param {object} data - json данные
         * @chain
         */
        $update(data) {
            const cached = this.$data;
            const parserMap = Object.create(null);
            let obj = cached;
            while (obj = Object.getPrototypeOf(obj)) {
                const map = obj[PARSE_MAP];
                if (!map) continue;
                for (const val in map) {
                    parserMap[val] = parserMap[val] || map[val];
                }
            }

            function parseValue(key,value){
                if (typeof value === "object") for (const subkey in value)  {
                    const newKey = key ? key+"."+subkey : subkey;
                    parseValue(newKey,value[subkey]);
                }
                if (!key) return;
                const mapDescriptor = parserMap[key];
                if (!mapDescriptor) return;
                const parse = mapDescriptor.parse;
                const result = parse.call(cached,value,data,key,cached);
                if (result !== undefined) cached[mapDescriptor.propertyName] = result;
            }
            parseValue(null,data);
            return this;
        }

        /**
         * Обновляет кэш объекта, без использования @Parse
         * Подставляет все данные из map в объект кэша
         * @param map
         * @chain
         */
        $updateRaw(map){
            const cached = this.$data;
            for (let key in map) {
                cached[key] = map[key];
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
                let result = null;
                // Если значение поля null, то на него не срабатывает функция сериализации
                if (this[key] !== null) {
                    result = serialize(value,serializedName,SERIALIZE_MODE_FULL);
                    if (result === undefined) continue;
                }
                // Сериализация для сложных ключей. Например assignment.status.oid
                // В этом случае, если @Serialize вернет 10, то произойдет json.assignment.status.oid = 10
                const parts = serializedName.split(".");
                let obj = json;
                for (let i = 0 ; i < parts.length-1; i++) { // Создаем нужные объекты по цепочке
                    obj[parts[i]] = obj[parts[i]] || {};
                    obj = obj[parts[i]]
                }
                obj[parts[parts.length-1]] = result // В финальный ключ заносим значение
            }
            // Добавляем трекер в json.
            json[ this.constructor.$tracker.dataField ] = this[ this.constructor.$tracker.ownField ];
            return json;
        }

        /**
         * Возвращает объект с измененными полями сущности
         * @chain
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

            for (let key in this.$data) {
                let val = this.$data[key];
                if ("$modifiedData" in Object(val) && val.checkModified()) fields.push(key);
            }
            for (let i = 0; i < fields.length; i++) {
                const key = fields[i];
                const value = this[key];
                const serializeDescriptor = serializeData[key];
                if (!serializeDescriptor) continue;
                const serialize = serializeDescriptor.serialize;
                const serializedName = serializeDescriptor.serializedName;
                const result = serialize(value,serializedName,SERIALIZE_MODE_MODIFIED);
                if (result === undefined) continue;
                const parts = serializedName.split(".");
                let obj = json;
                for (let i = 0 ; i < parts.length-1; i++) { // Создаем нужные объекты по цепочке
                    obj[parts[i]] = obj[parts[i]] || {};
                    obj = obj[parts[i]]
                }
                obj[parts[parts.length-1]] = result // В финальный ключ заносим значение
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
                const keys = Object.keys(this);
                const serializers = this[SERIALIZE_MAP];
                if (!serializers) return false;
                const serializeKeys = Object.keys(this[SERIALIZE_MAP]);
                for (let i = 0; i < keys.length; i++) {
                    let key = keys[i];
                    let serializeKey = serializeKeys.find((k) => k == key);
                    if (serializeKey) return true;
                }
                return false;
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

export {EntityProvider, SERIALIZE_MODE_FULL, SERIALIZE_MODE_MODIFIED};