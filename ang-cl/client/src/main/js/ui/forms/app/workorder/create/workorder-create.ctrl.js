import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";
import {BaseEntityCreate} from "../../entity-created/base-create";

@NGInjectClass()
class WorkorderCreateController extends BaseEntityCreate {
    // NG зависимости
    @NGInject() $scope;
    @NGInject() $location;
    @NGInject() SD;
    @NGInject() $state;
    @NGInject() ModalAction;
    @NGInject() $pageLock;
    @NGInject() workorder; // Новый наряд
    @NGInject() passedParams; // Переданные параметры

    async $onInit() {
        this.busy = "Initializing";
        this.loadingPromise = this.$initializeData();
        await this.loadingPromise;
        this.busy = false;

        this.stateCreated = "app.workorder.create.common";
        this.stateView = "app.workorder.card.view";
        this.stateList = "app.workorder.list";
        this.entity = this.workorder;
    }

    /**
     * Подгурзка всех данных будет в этом методе
     */
    async $initializeData(){
        if (this.passedParams.changeId) await this.$loadPassedChange();
        if (this.passedParams.problemId) await this.$loadPassedProblem();
        if (this.passedParams.templateId) await this.workorder.fillWithTemplate(this.passedParams.templateId)
    }

    async $loadPassedChange(){
        try {
            const change = await new this.SD.Change(this.passedParams.changeId).load();
            this.workorder.change = change;
        } catch (e) { // Не удалось загрузить такое изменение - удаляем его из переданных параметров
            this.passedParams.changeId = null;
            this.$location.search(this.passedParams)
        }
    }

    async $loadPassedProblem(){
        try {
            const problem = await new this.SD.Problem(this.passedParams.problemId).load();
            this.workorder.problem = problem;
        } catch (e) { // Не удалось загрузить такое изменение - удаляем его из переданных параметров
            this.passedParams.problemId = null;
            this.$location.search(this.passedParams)
        }
    }

    registerLeaveEditListener() {
        this.$pageLock(this.$scope)
            .setTitle("Несохраненные изменения")
            .setText("Внимание! Изменение не было сохранено, сохранить их перед уходом?")
            .setCondition(() => this.workorder.checkModified())
            .addAction("Да",async () => {
                // await this.workorder.create();
                return true;
            }).addAction("Нет", () => {
                return true;
            }).addAction("Отмена", () => false)
            .lock();
    }

    async _callCreatedFunc() {
        const createdWor = await this.workorder.create();
        await this.ModalAction.workorderCreated(this.$scope,{workorder:createdWor});
        this.$state.go(this.stateView, {workorderId: createdWor.id});
    }

}

export {WorkorderCreateController as controller}