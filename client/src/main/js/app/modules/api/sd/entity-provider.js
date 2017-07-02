function EntityProvider(){
    /**
     * Базовый класс для сущностей ServiceDesk
     * Содержит служебную логику, например кэш.
     */
    return class Entity{

        /**
         * Кэш объектов. Необходим, чтобы при обновлении какого-либо объекта не осталось его копий
         * со старыми данными.
         */
        static cache = {}; // Закэшированные сущности

        /**
         * Превращает json данные в SD сущность.
         */
        static parse(data){
            if (!data) return null;
            if (typeof data !== "object") return new this(data);
            return new this(data.id).$update(data)
        }

        /**
         * Создает сущность. Если не передать ID - считается, что создается новая сущность (которой нет в БД)
         * Она не попадет в кэш.
         * Если же ID передан - вернет новый объект, который наследуется от кэшированного.
         * @param {number} [id] - ID сущности
         * @returns {Entity|*}
         */
        constructor(id){
            if (id) {
                let entity = cache[id];
                if (!user) {
                    cache[id] = entity = Object.create(this.constructor.prototype);
                    entity.id = id;
                }
                Object.defineProperty(entity,"$data",{value: entity});
                return Object.create(entity)
            }
            const data = Object.create(this.constructor.prototype);
            Object.defineProperty(data,"$data",{value: data});
            return Object.create(data);
        }

        /**
         * Обновить объект в кэше.
         * Для каждого ключа из прешедших данных ищет у кэшированного объекта
         * поле parse:%key% и вызывает его, передвая туда значение data[%key%], сам объект data, ключ и
         * закэшированный объект. Если метод вернул что-либо не undefined - заносит это в поле cachedObject[%key%]
         * @param {object} data - json данные
         */
        $update(data){
            const cached = this.$data;
            for (let key in data) {
                const value = data[key];
                let parse = cached[`parse:${key}`];
                if (!parse) continue;
                const result = parse.call(cached,value,data,key,cached);
                if (result !== undefined) cached[key] = result;
            }
            return this;
        }

        /**
         * Необходимо для сравнения объектов через == и ===
         * @returns {*|undefined}
         */
        valueOf(){
            return this.id || undefined
        }

    }
}

export {EntityProvider};