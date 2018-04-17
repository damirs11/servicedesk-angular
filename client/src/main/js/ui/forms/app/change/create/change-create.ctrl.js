import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ChangeCreateController{
    // NG зависимости
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() $state;
    @NGInject() $pageLock;
    @NGInject() change; // Новое изменение - внедряется зависимостью.
    // Контроллер занят асинхронными задачами
    busy = false;
    // Произошла ошибка при создании
    errorCreating = false;

    headerTabs = [
        {name:'Общее',sref:'app.change.create.common'}
    ];

    requiredFields = [
        "category","classification","deadline","subject","description",
        "manager","initiator","priority","assignment.workgroup","assignment.executor"
    ];


    registerLeaveEditListener() {
        this.$pageLock(this.$scope)
            .setTitle("Несохраненные изменения")
            .setText("Внимание! Изменение не было сохранено, сохранить их перед уходом?")
            .setCondition(() => this.change.checkModified())
            .addAction("Да",async () => {
                await this.change.create();
                return true;
            }).addAction("Нет", () => {
                return true;
            }).addAction("Отмена", () => false)
            .lock();
    }

    get isRequiredFieldsFilled() {
        for(const fieldName of this.requiredFields) {
            const subnames = fieldName.split("."); // Сложное имя поля (пр. assignment.executor)
            let obj = this.change;
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
        this.busy = true;
        this.errorCreating = false;
        try {
            await this.change.create();
        } catch (e) {
            this.errorCreating = true;
        }
        this.busy = false;
    }

    get createButtonDisabled(){
        return this.busy || !this.isRequiredFieldsFilled
    }

    cancel(){
        this.$state.go("app.change.list");
    }


}

export {ChangeCreateController as controller}