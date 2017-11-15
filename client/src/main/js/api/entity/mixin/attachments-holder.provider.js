AttachmentsHolderProvider.$inject = ["$connector","SD"];
function AttachmentsHolderProvider($connector, SD) {
    /**
     * @mixin
     * @name ENTITY_MIXIN.AttachmentsHolder
     * @classdesc Реализует работу с вложениями
     */
    return class AttachmentsHolder {

        /**
         * Возвращает список голосов персон
         * @return {SD.Attachment[]}
         */
        async getAttachments(params){
            params = typeof params == "object" ? params : {};
            const votesData = await $connector.get(`rest/entity/${SD.Attachment.$entityType}?entityId=${this.id}`, params);
            return votesData.map(::SD.ApproverVote.parse)
        }

        /**
         * Получает количество вложений по переданному запросу
         */
        async getAttachments(params){
            params = typeof params == "object" ? params : {};
            return await $connector.get(`rest/entity/${SD.Attachment.$entityType}/count?entityId=${this.id}`,params);
        }

        /**
         * Прикрепляет файл к сущности.
         * @param fileInfo {SD.FileInfo} - прикрепляемый файл
         * @return {SD.Attachment}
         */
        async attachFile(fileInfo){
            const jsonData = fileInfo.$serialize();
            jsonData.entityId = this.id;
            jsonData.entityType = {id: this.constructor.$entityTypeId};
            const data = await $connector.post(`rest/entity/${SD.Attachment.$entityType}`,null,jsonData);
            return SD.Attachment.parse(data);
        }

    };
}

export {AttachmentsHolderProvider};