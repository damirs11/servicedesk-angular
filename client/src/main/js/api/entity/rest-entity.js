
RESTEntityProvider.$inject = ["Entity", "$connector"];
function RESTEntityProvider(Entity, $connector) {
    /**
     * редактируемая сущность.
     * @class
     * @classdesc Содержит служебную логику и методы load, list, etc.
     * @extends SD.Entity
     * @name SD.RESTEntity
     */
    return class RESTEntity extends Entity {


        /**
         * Возвращает строкой тип сущности. Используется для запросов
         * Должен соотвествовать типу на сервере
         * @returns {string}
         */
        get $entityType() {
            return this.constructor.name;
        }

        /**
         * Подгружает изменения в текущую сущность
         */
        async load(){
            const data = await $connector.get(`rest/entity/${this.$entityType}/${this.id}`);
            return this.$update(data);
        }

        /**
         * Осуществляет поиск сущностей по фильтру
         */
        static async list(params){
            const entityList = await $connector.get(`rest/entity/${this.name}`, params);
            return entityList.map(::this.parse)
        }

        /**
         * Получает общее количество записей по указанному фильтру
         */
        static async count(params){
            return await $connector.get(`rest/entity/${this.name}/count`, params);
        }

    };
}

export {RESTEntityProvider};