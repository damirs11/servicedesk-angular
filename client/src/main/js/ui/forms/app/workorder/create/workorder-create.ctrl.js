import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class WorkorderCreateController{
    // NG зависимости
    @NGInject() $scope;
    @NGInject() $location;
    @NGInject() SD;
    @NGInject() $state;
    @NGInject() ModalAction;
    @NGInject() $pageLock;
    @NGInject() workorder; // Новый наряд
    @NGInject() passedParams; // Переданные параметры

    busy = false;
    // Произошла ошибка при создании
    errorCreating = false;

    headerTabs = [
        {name:'Общее',sref:'app.workorder.create.common'}
    ];

    requiredFields = [
        "status","subject","description","category","initiator",
        "assignment.workgroup","assignment.executor", "deadline",
        "priority",
    ];


    async $onInit() {
        this.busy = "Initializing";
        this.loadingPromise = this.$initializeData();
        await this.loadingPromise;
        this.busy = false;
    }

    /**
     * Подгурзка всех данных будет в этом методе
     */
    async $initializeData(){
        if (this.passedParams.changeId) await this.$loadPassedChange();
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

    get isRequiredFieldsFilled() {
        for(const fieldName of this.requiredFields) {
            const subnames = fieldName.split("."); // Сложное имя поля (пр. assignment.executor)
            let obj = this.workorder;
            for (let i = 0; i < subnames.length; i++) {
                const subname = subnames[i];
                const value = obj[subname];
                if (value == null) return false;
                obj = obj[subname]
            }
        }
        return true;
    }

    async create(){
        if (this.busy) return;
        this.busy = "Saving";
        this.errorCreating = false;
        try {
            const createdWor = await this.workorder.create();
            await this.ModalAction.workorderCreated(this.$scope,{workorder:createdWor});
            this.$state.go("app.workorder.card.view",{workorderId: createdWor.id})
        } catch (e) {
            this.errorCreating = true;
            this.busy = false;
            throw e;
        }
        this.busy = false;
    }

    get createButtonDisabled(){
        return this.busy || !this.isRequiredFieldsFilled
    }

    cancel(){
        this.$state.go("app.workorder.list");
    }


}

export {WorkorderCreateController as controller}