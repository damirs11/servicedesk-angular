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
            const data = await $connector.patch(`rest/entity/${this.constructor.$entityType}/${this.id}`,null,jsonData);
            this.reset();
            this.$update(data)
        }

        /**
         * Создает новую сущность
         * Создается новый объект по образцу текущего.
         * @return {SD.EditableEntity}
         */
        async create(){
            const jsonData = this.$serialize();
            const data = await $connector.post(`rest/entity/${this.constructor.$entityType}`,null,jsonData);
            return this.constructor.parse(data);
        }
    }
    return EditableEntity;
}

export {EditableEntityProvider};