
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
            const linesData = await $connector.get(`rest/entity/${this.$entityType}/${this.id}/history`, params);
            return linesData.map(::SD.HistoryLine.parse)
        }

        /**
         * Получает общее количество записей по указанному фильтру
         */
        async getHistoryCount(params){
            params = typeof params == "object" ? params : {};
            return await $connector.get(`rest/entity/${this.$entityType}/${this.id}/history/count`,params);
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
    };
}

export {HistoryableProvider};