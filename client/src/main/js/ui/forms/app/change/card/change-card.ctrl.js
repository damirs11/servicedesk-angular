import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";
import {CHANGE_STATUSES, APPROVAL_STATUSES} from "../../../../../api/entity/util/status-list";

@NGInjectClass()
class ChangeCardController {
    /**
     * Занят ли контроллер. Будет отображат анимацию загрузки
     * @type {string}
     */
    busy;
    /**
     * Ошибка при загрузке
     * Или ошибка прав
     * @type {Error}
     */
    loadingError;
    /**
     * Изменение
     * @type {SD.Change}
     */
    change;
    /**
     * Права доступа;
     * @type {SDAccessRules}
     */
    accessRules;
    /**
     * Промис загрузки изменения
     */
    loadingPromise;
    /**
     * Вкладки в хидере карточки
     */
    headerTabs = [
        {name:'Просмотр',sref:'app.change.card.view'},
        {name:'История',sref:'app.change.card.history',
            disabled:() => this.accessRules && !this.accessRules.isReadHistoryAllowed},
        {name:'Согласование',sref:'app.change.card.approval',
            disabled:() => this.accessRules && !this.canSeeApprovalPage},
        {name:'Вложения',sref:'app.change.card.attachments',
            disabled:() => this.accessRules && !this.accessRules.isReadAttachmentsAllowed},
        {name:'Наряды',sref:'app.change.card.workorders'},
    ];

    @NGInject() SD;
    @NGInject() changeId;
    @NGInject() Session;

    async $onInit(){
        this.loadingPromise = this.loadData();
        await this.loadingPromise;
        this.accessRules = this.change.accessRules;
    }

    async loadData(){
        await this.$loadChange();
        await this.$loadAccess();
        await this.$loadStatuses();
        await this.$loadApproval();
    }

    async $loadStatuses() {
        this.statusList = await this.SD.EntityStatus.list({entityTypeId:this.SD.Change.$entityTypeId});
    }

    async $loadApproval() {
        this.approval = await this.change.getApproval()
    }

    get loading(){
        return this.busy === "loading";
    }

    get loadingErrorIsNotFound(){
        return this.loadingError && this.loadingError.status === 404;
    }

    get loadingErrorIsServerOffline(){
        return this.loadingError && this.loadingError.status === -1;
    }

    get loadingErrorIsReadDisallowed(){
        return this.loadingError && this.loadingError.reason == "readDisallowed"
    }

    get loadingErrorIsCustom(){
        return !this.loadingErrorIsNotFound &&
            !this.loadingErrorIsServerOffline &&
            !this.loadingErrorIsReadDisallowed
        ;
    }

    async $loadAccess() {
        await this.change.updateAccessRules();

        if (!this.change.accessRules.isReadEntityAllowed) {
            this.loadingError = {reason: "readDisallowed"};
            throw this.loadingError
        }
    }

    async $loadChange(){
        try {
            this.busy = "loading";
            this.change = await new this.SD.Change(this.changeId).load();
        } catch (error) {
            this.loadingError = error || true;
            throw error;
        }
    }

    get canSeeApprovalPage() {
        return this.accessRules.isReadApprovalAllowed
            && this.approval
    }

    get $isManager(){
        return this.change.manager
            && (this.change.manager.id == this.Session.user.person.id)
    }
}

export {ChangeCardController as controller}