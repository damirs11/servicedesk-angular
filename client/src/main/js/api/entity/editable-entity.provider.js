
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
            // ToDo сущность должна появиться в кэше.
            const jsonData = this.$changedData;
            await $connector.patch(`rest/entity/${this.$entityType}/${this.id}`,null,jsonData);
        }

        /**
         * Создает новую сущность
         */
        async create(){
            const jsonData = this.$serialize();
            await $connector.put(`rest/entity/${this.$entityType}`,null,jsonData);
        }
    };
}

export {EditableEntityProvider};