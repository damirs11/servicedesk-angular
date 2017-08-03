
EditableEntityProvider.$inject = ["Entity", "SD","$connector"];
function EditableEntityProvider(Entity, SD, $connector) {
    /**
     * редактируемая сущность.
     * @class
     * @classdesc Реализует методы для работы с REST: save, create
     * @extends SD.Entity
     * @name SD.EditableEntity
     */
    return class EditableEntity extends Entity {

        /**
         * Сохраняет изменения текущей сущнсоти
         */
        async save(){
            const jsonData = this.$modifiedData;
            const data = await $connector.patch(`rest/entity/${this.$entityType}/${this.id}`,null,jsonData);
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
    };
}

export {EditableEntityProvider};