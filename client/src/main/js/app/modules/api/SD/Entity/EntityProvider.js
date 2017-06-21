export function EntityProvider(){
    /**
     * Базовый класс для сущностей ServiceDesk
     * Содержит служебную логику, например кэш.
     */
    return class Entity{

        static cache = {}; // Закэшированные сущности

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
         * @param data
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


    }
}