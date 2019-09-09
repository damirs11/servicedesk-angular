import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ServiceCallCardController {
    
    @NGInject() SD;
    @NGInject() entity;

    /**
     * Занят ли контроллер. Будет отображать анимацию загрузки
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
     * Промис загрузки изменения
     */
    loadingPromise;
    /**
     * Вкладки в хидере карточки
     */
    headerTabs = [
        {name:'Просмотр',sref:'app.servicecall.card.view'},
        {name:'История',sref:'app.servicecall.card.history',
            disabled:() => this.entity.accessRules && !this.entity.accessRules.isReadHistoryAllowed},
        {name:'Вложения',sref:'app.servicecall.card.attachments',
            disabled:() => this.entity.accessRules && !this.entity.accessRules.isReadAttachmentsAllowed},
        {name:'Наряды',sref:'app.servicecall.card.workorders'}
    ];

    async $onInit(){
        console.debug('servicecall card init');
        await (this.loadingPromise = this.loadData());
    }

    async loadData(){
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