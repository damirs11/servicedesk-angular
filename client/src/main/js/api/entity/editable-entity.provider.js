EditableEntityProvider.$inject = ["RESTEntity", "$connector"];

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
         * @param [data] {Object} - данные для сохранения
         *   Если передать data - отправит этот объект запросом сохранения.
         *   Если не передавать - соберет значения измененых полей через $modifiedData
         * @link $modifiedData
         * @return {SD.EditableEntity}
         */
        async save(data) {
            const jsonData = data || this.$modifiedData;
            const respData = await $connector.patch(`rest/entity/${this.constructor.$entityType}/${this.id}`, null, jsonData);
            this.reset();
            this.$update(respData);
            return this;
        }

        /**
         * Создает новую сущность
         * Возвращает новую сущность, созданную по данным
         * пришедшим с сервера. Текущий объект не обновляется
         * @return {SD.EditableEntity}
         */
        async create() {
            const jsonData = this.$serialize();
            const data = await $connector.post(`rest/entity/${this.constructor.$entityType}`, null, jsonData);
            return this.constructor.parse(data);
        }

        /**
         * Удаляет сущность
         * @return {void}
         */
        async delete() {
            await $connector.delete(`rest/entity/${this.constructor.$entityType}/${this.id}`);
        }
    }

    return EditableEntity;
}

export {EditableEntityProvider};