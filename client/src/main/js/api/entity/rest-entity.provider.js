RESTEntityProvider.$inject = ["Entity", "$connector"];
function RESTEntityProvider(Entity, $connector) {
    /**
     * Содержит основные методы для чтения сущностей
     * @class
     * @classdesc Содержит служебную логику и методы load, list, etc.
     * @extends SD.Entity
     * @name SD.RESTEntity
     */
    return class RESTEntity extends Entity {
        /**
         * Подгружает изменения в текущую сущность
         */
        async load(){
            const data = await $connector.get(`rest/entity/${this.constructor.$entityType}/${this.id}`);
            return this.$update(data);
        }

        /**
         * Осуществляет поиск сущностей по фильтру
         */
        static async list(params){
            const entityList = await $connector.get(`rest/entity/${this.$entityType}`, params);
            return entityList.map(::this.parse)
        }

        /**
         * Получает общее количество записей по указанному фильтру
         */
        static async count(params){
            return await $connector.get(`rest/entity/${this.$entityType}/count`, params);
        }


        /**
         * Заполняет сущность по шаблону
         * @param template {SD.Template|number} - шаблон или id шаблона
         * @return {Promise.<Entity>}
         */
        async fillWithTemplate(template){
            const templateId = typeof template === "object" ? template.id : template;
            const data = await $connector.get(`rest/entity/${this.constructor.$entityType}/template/${templateId}`);
            this.$update(data);
            return this;
        }
    };
}

export {RESTEntityProvider};