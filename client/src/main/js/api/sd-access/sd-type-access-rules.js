import {NGInject, NGInjectClass} from "../../common/decorator/ng-inject.decorator";
import {SDACCESS_FIELD_HIDE, SDACCESS_FIELD_READ, SDACCESS_FIELD_WRITE} from "./sd-access";
import {Enumerable} from "../../common/decorator/enumerable.decorator";

@NGInjectClass()
class SDTypeAccessRules {
    @NGInject() $injector;

    @NGInject() serverAccessData;
    @NGInject() actionRules;
    _localActionRules = {};
    _maxActionAccessMap = {};

    /**
     * Отличается от SDAccess#addActionAccessRule тем, что
     * действует лишь на этот экземпляр SDTypeAccessRules (локальные правила)
     * @see SDAccess#addActionAccessRule
     */
    addActionAccessRule(actionName,rule){
        let rules = this._localActionRules[actionName];
        if (!rules) rules = this._localActionRules[actionName] = [];
        rules.push(rule);
    }

    /**
     * Задает максимальный доступ к действию. Работает как правило,
     * которое всегда выдает accessible.
     * Можно сделать поля всегда недоступным: setActionAccessible(false);
     * @param field
     * @param accessible
     */
    setActionAccessible(field,accessible) {
        this._maxActionAccessMap[field] = accessible
    }

    actionAllowed(action) {
        if (this._maxActionAccessMap[action] == false) return false;

        if (this.serverAccessData[action] == "NONE") return false;

        const localRules = this._localActionRules[action];
        if (localRules) for (let i = 0; i < localRules.length; i++) {
            const result = this.$injector.invoke(localRules[i]);
            if (!result) return false
        }

        const globalRules = this.actionRules[action];
        if (globalRules) for (let i = 0; i < globalRules.length; i++) {
            const result = this.$injector.invoke(globalRules[i]);
            if (!result) return false
        }

        return true;
    }

    get isReadEntityAllowed(){ return this.actionAllowed("read") }
    get isCreateEntityAllowed(){ return this.actionAllowed("create") }
    get isDeleteEntityAllowed(){ return this.actionAllowed("delete") }
    get isUpdateEntityAllowed(){ return this.actionAllowed("update") }

    get isReadHistoryAllowed(){ return this.actionAllowed("historyRead") }
    get isCreateHistoryAllowed(){ return this.actionAllowed("historyCreate") }
    get isDeleteHistoryAllowed(){ return this.actionAllowed("historyDelete") }
    get isUpdateHistoryAllowed(){ return this.actionAllowed("historyUpdate") }

    get isReadApprovalAllowed(){ return this.canSee("approval") }
    get isUpdateApprovalAllowed(){ return this.canUpdate("approval") }

    get isReadAttachmentsAllowed(){ return this.canSee("attachment") }
    get isUpdateAttachmentsAllowed(){ return this.canUpdate("attachment") }
}

export {SDTypeAccessRules}