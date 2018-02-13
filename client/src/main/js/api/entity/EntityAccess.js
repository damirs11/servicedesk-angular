import {Parse} from "./decorator/parse.decorator";

EntityAccessProvider.$inject = ["Entity"];
function EntityAccessProvider(Entity) {
    /**
     * Доступ к сущности.
     * ! Важно !
     * Т.к. сущность не имеет своего трекера (id), онн должен добавляться искуственно:
     * При вызове .parse(data) в data предварительно заностся id сущности к которой
     * привязан этот класс.
     * Если не передать трекер, сущность не будет кэшироваться.
     * @class
     * @name SD.EntityAccess
     * @extends SD.Entity
     */
    return class EntityAccess extends Entity {
        /**
         * Доступ к полям
         * @property
         * @name SD.EntityAccess#fieldAccess
         * @type {*}
         */
        @Parse("attributes", (data) => {
            return Object.keys(data).reduce((accessMap,key) => {
                accessMap[key] = new EntityFieldAccess(data[key]);
                return accessMap;
            }, {});
        }) fieldAccess;

        /**
         * Доступ к действиям
         * @property
         * @name SD.EntityAccess#actionAccess
         * @type {*}
         */
        @Parse("entity", (data) => {
            return Object.keys(data).reduce((accessMap,key) => {
                accessMap[key] = new EntityActionAccess(data[key]);
                return accessMap;
            }, {});
        }) actionAccess;

        canEditField(field) {
            return this.fieldAccess[field] && this.fieldAccess[field].canEdit
        }

        canSeeField(field) {
            return this.fieldAccess[field] && this.fieldAccess[field].canSee
        }

        actionAllowed(action) {
            return this.fieldAccess[action] && this.fieldAccess[action].allowed
        }

    };
}

class EntityFieldAccess{
    static VALUES = {};

    constructor(name) {
        if (this.constructor.VALUES[name]) return this.constructor.VALUES[name];
        this.name = name;
        Object.freeze(this);
    }

    get canSee(){
        return this.name == "READ" || this.name == "UPDATE"
    }

    get canEdit(){
        return this.name == "UPDATE"
    }
}
EntityFieldAccess.VALUES = {
    "HIDE": new EntityFieldAccess("HIDE"),
    "READ": new EntityFieldAccess("READ"),
    "UPDATE": new EntityFieldAccess("UPDATE"),
};


class EntityActionAccess{
    static VALUES = {};

    constructor(name) {
        if (this.constructor.VALUES[name]) return this.constructor.VALUES[name];
        this.name = name;
    }

    get allowed(){
        return this.name != "NONE"
    }
}
EntityActionAccess.VALUES = {
    "NONE": new EntityActionAccess("NONE"),
    "ALWAYS": new EntityActionAccess("ALWAYS"),
};

export {EntityAccessProvider};