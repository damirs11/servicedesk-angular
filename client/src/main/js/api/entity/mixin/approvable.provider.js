ApprovableProvider.$inject = ["$connector","SD"];
function ApprovableProvider($connector, SD) {
    /**
     * @mixin
     * @name ENTITY_MIXIN.Approvable
     * @classdesc Реализует работу с голосованием
     */
    return class Approvable {

        /**
         * Возвращает список голосов персон
         * @return {SD.ApproverVote[]}
         */
        async getApproverVotes(params){
            params = typeof params == "object" ? params : {};
            const votesData = await $connector.get(`rest/entity/ApproverVote?entityId=${this.id}`, params);
            return votesData.map(::SD.ApproverVote.parse)
        }

        /**
         * Получает количество голосов персон по переданному запросу
         */
        async getApproverVotesCount(params){
            params = typeof params == "object" ? params : {};
            return await $connector.get(`rest/entity/ApproverVote/count?entityId=${this.id}`,params);
        }

    };
}

export {ApprovableProvider};