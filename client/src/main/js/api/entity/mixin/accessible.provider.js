AccessibleProvider.$inject = ["$connector","SD"];
function AccessibleProvider($connector, SD) {
    /**
     * @mixin
     * @name ENTITY_MIXIN.Accessible
     * @classdesc Реализует работу с доступом к сущности
     */
    return class Accessible {

        /**
         * В сущностях будет поле permissions
         * @type SD.EntityAccess
         */
        _entityAccess;

        async getEntityAccess(){
            if (this._entityAccess) return this._entityAccess;
            this._entityAccess = await this.loadEntityAccess();
            return this._entityAccess
        }


        /**
         * Подгружает права доступа к сущности.
         * @returns {SD.EntityAccess}
         */
        async loadEntityAccess(){
            const entityAccessData = await $connector.get(
                `rest/service/security/access/${this.constructor.$entityType}/${this.id}`
            );
            entityAccessData["id"] = this.id; // Чтобы сущность кэшировалась; см. док к классу EntityAccess
            return SD.EntityAccess.parse(entityAccessData);
        }


    };
}

export {AccessibleProvider};