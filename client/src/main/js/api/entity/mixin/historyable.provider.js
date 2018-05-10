
HistoryableProvider.$inject = ["$connector","SD"];
function HistoryableProvider($connector, SD) {
    /**
     * @mixin
     * @name ENTITY_MIXIN.Historyable
     * @classdesc Реализует работу с историей и чатом
     */
    return class Historyable {

        /**
         * Возвращает коллекцию записей в истории
         * @return {SD.HistoryLine[]}
         */
        async getHistory(params){
            params = typeof params == "object" ? params : {};
            params.entityType = this.constructor.$entityType;
            params.entityId = this.id;
            const linesData = await $connector.get(`rest/service/history`, params);
            return linesData.map(::SD.HistoryLine.parse)
        }

        /**
         * Получает общее количество записей по указанному фильтру
         */
        //todo удалить метод, вместо этого в методе getHistory извлекать данное значение
        // из Header-параметра "Content-Range". Формат "from-to/amount". Например: "26-50/456"
        // означает, чтобы были возвращены данные с 26 по 50 запись включительно, всего записей 456
        async getHistoryCount(params){
            params = typeof params == "object" ? params : {};
            return await $connector.get(`rest/entity/${this.constructor.$entityType}/${this.id}/history/count`,params);
        }

        /**
         * Возвращает записи чата
         * @return {SD.HistoryLine[]}
         */
        async getChat(params){
            params = typeof params == "object" ? params : {};
            params.chat = true;
            return this.getHistory(params);
        }

        /**
         * Возвращает количество сообщений в чате
         */
        async getChatCount(params){
            params = typeof params == "object" ? params : {};
            params.chat = true;
            return this.getHistoryCount(params);
        }

        /**
         * Отправляет сообщение в чат сущности.
         * @param text {String} - текст сообщения
         * @return {Promise.<void>}
         */
        async sendChatMessage(text) {
            const jsonData = {
                entityId: this.id,
                entityType: this.constructor.$entityTypeId,
                message: text
            };
            await $connector.post(`rest/service/history`,null,jsonData);
        }
    };
}

export {HistoryableProvider};