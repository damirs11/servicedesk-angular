AccessibleProvider.$inject = ["$connector","SD"];
function AccessibleProvider($connector, SD) {
    /**
     * @mixin
     * @name ENTITY_MIXIN.Accessible
     * @classdesc Реализует работу с доступом к сущности
     */
    return class Accessible {

        /**
         * @type SD.EntityAccess
         */
        entityAccess;


        /**
         * Подгружает права доступа к сущности.
         * @returns {SD.EntityAccess}
         */
        async updateEntityAccess(){
            const entityAccessData = await $connector.get(
                `rest/service/security/access/${this.constructor.$entityType}/${this.id}`
            );
            entityAccessData["id"] = this.id; // Чтобы сущность кэшировалась; см. док к классу EntityAccess
            this.$updateRaw({entityAccess:SD.EntityAccess.parse(entityAccessData)});
        }


    };
}

export {AccessibleProvider};