AccessibleProvider.$inject = ["$sdAccess"];
function AccessibleProvider($sdAccess) {
    /**
     * @mixin
     * @name ENTITY_MIXIN.Accessible
     * @classdesc Реализует работу с доступом к сущности
     */
    return class Accessible {

        /**
         * @type SDAccessRules
         */
        accessRules;


        /**
         * Подгружает права доступа к сущности.
         */
        async updateAccessRules(){
            const accessRules = await $sdAccess.loadAccessRules(this);
            this.$updateRaw({accessRules})
        }


    };
}

export {AccessibleProvider};