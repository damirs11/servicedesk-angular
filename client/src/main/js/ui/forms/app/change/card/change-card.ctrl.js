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
        await this.$loadStatuses();
    }

    async $loadStatuses() {
        this.statusList = await this.SD.EntityStatus.list({entityTypeId:this.SD.Change.$entityTypeId});
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

    async $loadChange(){
        if (this.busy) throw new Error("Controller is busy " + this.busy);
        try {
            this.busy = "loading";
            this.change = await  new this.SD.Change(this.changeId).load();
        } catch (error) {
            this.loadingError = error || true;
        } finally {
            this.busy = null;
        }
    }
}

export {ChangeCardController as controller}