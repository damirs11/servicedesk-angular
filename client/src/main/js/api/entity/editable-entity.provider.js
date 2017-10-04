EditableEntityProvider.$inject = ["RESTEntity","$connector"];
function EditableEntityProvider(RESTEntity, $connector) {
    /**
     * редактируемая сущность.
     * @class
     * @name SD.EditableEntity
     * @classdesc Реализует методы для работы с редактированием сущности
     * @extends SD.RESTEntity
     */
    class EditableEntity extends RESTEntity {

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
            const data = await $connector.post(`rest/entity/${this.$entityType}`,null,jsonData);
            this.$update(data)
        }
    }
    return EditableEntity;
}

export {EditableEntityProvider};