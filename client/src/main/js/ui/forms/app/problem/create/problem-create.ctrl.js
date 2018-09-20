import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ProblemCreateController{
    // NG зависимости
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() $state;
    @NGInject() $pageLock;
    @NGInject() ModalAction;
    @NGInject() passedParams;
    @NGInject() problem; // Новое изменение - внедряется зависимостью.
    // Контроллер занят асинхронными задачами
    busy = false;
    // Произошла ошибка при создании
    errorCreating = false;

    headerTabs = [
        {name:'Общее',sref:'app.problem.create.common'}
    ];

    requiredFields = [
        "category","classification","deadline","subject","description","configurationItem",
        "manager","initiator","priority","assignment.workgroup","assignment.executor"
    ];

    async $onInit(){
        const templateId = this.passedParams.templateId;
        if (templateId) await this.problem.fillWithTemplate(templateId)
    }

    get isRequiredFieldsFilled() {
        for(const fieldName of this.requiredFields) {
            const subnames = fieldName.split("."); // Сложное имя поля (пр. assignment.executor)
            let obj = this.problem;
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
            const createdProblem = await this.problem.create();
            await this.ModalAction.problemCreated(this.$scope,{problem:createdProblem});
            this.$state.go("app.problem.card.view",{problemId: createdProblem.id})
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
        this.$state.go("app.problem.list");
    }


}

export {ProblemCreateController as controller}