AttachmentsHolderProvider.$inject = ["$connector","SD"];
function AttachmentsHolderProvider($connector, SD) {
    /**
     * @mixin
     * @name ENTITY_MIXIN.AttachmentsHolder
     * @classdesc Реализует работу с вложениями
     */
    return class AttachmentsHolder {

        /**
         * Возвращает список вложений
         * @return {SD.Attachment[]}
         */
        async getAttachments(params){
            params = typeof params === "object" ? params : {};
            const data = await $connector.get(`rest/entity/${SD.Attachment.$entityType}?entityId=${this.id}`, params);
            return data.map(::SD.Attachment.parse)
        }

        /**
         * Получает количество вложений по переданному запросу
         * @return {Number}
         */
        async getAttachmentsCount(params){
            params = typeof params === "object" ? params : {};
            return await $connector.get(`rest/entity/${SD.Attachment.$entityType}/count?entityId=${this.id}`,params);
        }

        /**
         * Прикрепляет файл к сущности.
         * @param fileInfo {SD.FileInfo} - прикрепляемый файл
         * @return {Promise.<SD.Attachment>}
         */
        async attachFile(fileInfo){
            const jsonData = fileInfo.$serialize();
            jsonData.entityId = this.id;
            jsonData.entityType = {id: this.constructor.$entityTypeId};
            jsonData.path = fileInfo.path;
            const data = await $connector.post(`rest/entity/${SD.Attachment.$entityType}`,null,jsonData);
            return SD.Attachment.parse(data);
        }

    };
}

export {AttachmentsHolderProvider};