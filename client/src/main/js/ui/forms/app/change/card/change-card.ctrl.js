import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

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
     * Промис загрузки изменения
     */
    loadingPromise;
    /**
     * Вкладки в хидере карточки
     */
    headerTabs = [
        {name:'Просмотр',sref:'app.change.card.view'},
        {name:'История',sref:'app.change.card.history',
            disabled:() => this.entityAccess && !this.entityAccess.readHistoryAllowed},
        {name:'Согласование',sref:'app.change.card.approval',
            disabled:() => this.entityAccess && !this.entityAccess.readApprovalAllowed},
        {name:'Вложения',sref:'app.change.card.attachments',
            disabled:() => this.entityAccess && !this.entityAccess.readAttachmentsAllowed},
    ];

    @NGInject() SD;
    @NGInject() changeId;

    async $onInit(){
        this.loadingPromise = this.loadData();
        await this.loadingPromise;
        this.entityAccess = this.change.entityAccess
    }

    async loadData(){
        await this.$loadChange();
        await this.$loadAccess();
        await this.$loadStatuses();
    }

    async $loadStatuses() {
        this.statusList = await this.SD.EntityStatus.list({entityTypeId:this.SD.Change.$entityTypeId});
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
        try {
            await this.change.updateEntityAccess();
        } catch (error) {
            this.loadingError = error || true;
            throw error;
        }
        if (!this.change.entityAccess.readEntityAllowed) {
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
}

export {ChangeCardController as controller}