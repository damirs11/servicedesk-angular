import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";
import {TYPEID_WORKORDER} from "../../../../../api/entity/util/entity-type-list";
@NGInjectClass()
class WorkorderViewController {
    /**
     * Занят ли контроллер. Будет отображат анимацию загрузки
     * @type {string|null}
     */
    busy = null;
    /**
     * Ошибка при загрузке
     * @type {Error}
     */
    loadingError;
    /**
     * Изменение
     * @type {SD.Workorder}
     */
    workorder;
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
        {name:'Просмотр',sref:'app.workorder.card.view'},
        {name:'История',sref:'app.workorder.card.history',
            disabled:() => this.accessRules && !this.accessRules.isReadHistoryAllowed},
        {name:'Вложения',sref:'app.workorder.card.attachments',
            disabled:() => this.accessRules && !this.accessRules.isReadAttachmentsAllowed},
    ];

    @NGInject() SD;
    @NGInject() workorderId;
    @NGInject() Session;

    async $onInit() {
        this.loadingPromise = this.loadData();
        await this.loadingPromise;
        this.accessRules = this.workorder.accessRules;
    }

    async loadData(){
        await this.$loadWorkorder();
        await this.$loadAccess();
        await this.$loadStatuses();
    }

    get loading(){
        return this.busy === "loading";
    }

    get loadingErrorIsNotFound(){
        return this.loadingError.status === 404;
    }

    get loadingErrorIsServerOffline(){
        return this.loadingError.status === -1;
    }

    get loadingErrorIsCustom(){
        return !this.loadingErrorIsNotFound &&
            !this.loadingErrorIsServerOffline
            ;
    }


    async $loadStatuses() {
        this.statusList = await this.SD.EntityStatus.list({entityTypeId:TYPEID_WORKORDER});
    }

    async $loadAccess() {
        await this.workorder.updateAccessRules();

        if (!this.workorder.accessRules.isReadEntityAllowed) {
            this.loadingError = {reason: "readDisallowed"};
            throw this.loadingError
        }
    }

    async $loadWorkorder(){
        try {
            this.busy = "loading";
            this.workorder = await new this.SD.Workorder(this.workorderId).load();
        } catch (error) {
            this.loadingError = error || true;
            throw error;
        }
    }
}

export {WorkorderViewController as controller}