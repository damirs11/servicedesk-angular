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
     * Дочерние стейты, которые будут отображаться "вкладками"
     * Название стейта = ${current.name}.${path}
     */
    childStates = [
        {path: "view", name: "Просмотр"}, {path: "history", name: "История"},
        {path: "approval", name: "Согласование"}, {path: "attachments", name: "Вложения"}
    ];

    @NGInject() SD;
    @NGInject() changeId;

    async $onInit(){
        this.loadingPromise = this.loadData();
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

    get errorReadDisallowed(){
        return this.loadingError && this.loadingError.reason == "readDisallowed"
    }

    get loadingErrorIsCustom(){
        return !this.loadingErrorIsNotFound &&
            !this.loadingErrorIsServerOffline &&
            !this.errorReadDisallowed
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