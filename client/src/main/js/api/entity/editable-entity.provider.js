
EditableEntityProvider.$inject = ["RESTEntity","$connector","SD"];
function EditableEntityProvider(RESTEntity, $connector, SD) {
    /**
     * редактируемая сущность.
     * @class
     * @name SD.EditableEntity
     * @classdesc Реализует методы для работы с редактированием сущности
     * @extends SD.RESTEntity
     */
    return class EditableEntity extends RESTEntity {

        /**
         * Сохраняет изменения текущей сущнсоти
         */
        async save(){
            const jsonData = this.$modifiedData;
            const data = await $connector.patch(`rest/entity/${this.$entityType}/${this.id}`,null,jsonData);
            this.reset();
            this.$update(data)
        }

        /**
         * Создает новую сущность
         * @return {SD.EditableEntity}
         */
        async create(){
            // ToDo сущность должна появиться в кэше.
            const jsonData = this.$serialize();
            const data = await $connector.put(`rest/entity/${this.$entityType}`,null,jsonData);
            this.$update(data)
        }

        /**
         * Возвращает коллекцию записей в истории
         * @return {SD.HistoryLine[]}
         */
        async getHistory(params){
            params = typeof params == "object" ? params : {};
            params.entity = this.id;
            const linesData = await $connector.get(`rest/entity/${this.$entityType}HistoryLine`, params);
            return linesData.map(::SD.HistoryLine.parse)
        }

        /**
         * Получает общее количество записей по указанному фильтру
         */
        async getHistoryCount(params){
            params = typeof params == "object" ? params : {};
            params.entity = this.id;
            return await $connector.get(`rest/entity/${this.$entityType}HistoryLine/count`,params);
        }
    };
}

export {EditableEntityProvider};