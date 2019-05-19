import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ServiceCallCreateController{
    // NG зависимости
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() $state;
    @NGInject() $pageLock;
    @NGInject() ModalAction;
    @NGInject() passedParams;
    @NGInject() entity; // Новая заявка - внедряется зависимостью.
    // Контроллер занят асинхронными задачами
    busy = false;
    // Произошла ошибка при создании
    errorCreating = false;

    headerTabs = [
        {name:'Общее',sref:'app.serviceсall.create.common'}
    ];

    requiredFields = [
        "category",
        "classification",
        "subject",
        "description",
        "organization",
        "service",
        "initiator",
        "priority",
        "assignment.workgroup",
        "assignment.executor"
    ];

    async $onInit(){
        const templateId = this.passedParams.templateId;
        if (templateId) await this.entity.fillWithTemplate(templateId);
    }

    get isRequiredFieldsFilled() {
        for(const fieldName of this.requiredFields) {
            const subnames = fieldName.split("."); // Сложное имя поля (пр. assignment.executor)
            let obj = this.entity;
            for (let i = 0; i < subnames.length; i++) {
                const subname = subnames[i];
                const value = obj[subname];
                if (value == null) return false;
                obj = obj[subname];
            }
        }
        return true;
    }

    async create(){
        if (this.busy) return;
        this.busy = true;
        this.errorCreating = false;
        try {
            //todo временно удаляем "крайний срок" из заявки, так как поле readonly при создании
            delete this.entity.deadline;

            const createdEntity = await this.entity.create();
            await this.ModalAction.serviceCallCreated(this.$scope, {entity:createdEntity});
            this.$state.go("app.serviceсall.card.view",{entityId: createdEntity.id});
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
        this.$state.go("app.servicecall.list");
    }

}

export {ServiceCallCreateController as controller}