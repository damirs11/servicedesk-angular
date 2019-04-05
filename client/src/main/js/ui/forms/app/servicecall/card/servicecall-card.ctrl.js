import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ServiceCallCardController {
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
     * Заявка
     * @type {SD.ServiceCall}
     */
    serviceCall;
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
        {name:'Просмотр',sref:'app.servicecall.card.view'},
        {name:'История',sref:'app.servicecall.card.history',
            disabled:() => this.accessRules && !this.accessRules.isReadHistoryAllowed},
        {name:'Вложения',sref:'app.servicecall.card.attachments',
            disabled:() => this.accessRules && !this.accessRules.isReadAttachmentsAllowed},
        {name:'Наряды',sref:'app.servicecall.card.workorders'}
    ];

    @NGInject() SD;
    @NGInject() serviceCallId;
    @NGInject() Session;

    async $onInit(){
        this.loadingPromise = this.loadData();
        await this.loadingPromise;
        this.accessRules = this.serviceCall.accessRules;
    }

    async loadData(){
        await this.$loadServiceCall();
        await this.$loadAccess();
        await this.$loadStatuses();
    }

    async $loadServiceCall(){
        try {
            this.busy = "loading";
            this.serviceCall = await new this.SD.ServiceCall(this.serviceCallId).load();
        } catch (error) {
            this.loadingError = error || true;
            throw error;
        }
    }

    async $loadAccess() {
        await this.serviceCall.updateAccessRules();
    }

    async $loadStatuses() {
        this.statusList = await this.SD.EntityStatus.list({entityTypeId: this.SD.ServiceCall.$entityTypeId});
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

    get loadingErrorIsCustom(){
        return !this.loadingErrorIsNotFound &&
            !this.loadingErrorIsServerOffline
        ;
    }
    get customErrorText() {
        if (this.loadingError && this.loadingError.data.text) return this.loadingError.data.text;
        return this.loadingError.toString();
    }
}

export {ServiceCallCardController as controller}