class BaseEntityCreate {

    // Контроллер занят асинхронными задачами
    busy = false;
    // Произошла ошибка при создании
    errorCreating = false;

    requiredFields = [
        "category",
        "classification",
        "deadline",
        "subject",
        "description",
        "manager",
        "initiator",
        "priority",
        "assignment.workgroup",
        "assignment.executor"
    ];

    async $onInit(){
        const templateId = this.passedParams.templateId;
        if (templateId) await this.problem.fillWithTemplate(templateId);
        this.headerTabs = [
            {name:'Общее', sref: this.stateCreated}
        ];
    }

    async create(){
        if (this.busy) return;
        this.busy = true;
        this.errorCreating = false;
        try {
            await this._callCreatedFunc();
        } catch (e) {
            this.errorCreating = true;
            this.busy = false;
            throw e;
        }
        this.busy = false;
    }

    cancel(){
        this.$state.go(this.stateList);
    }

    get createButtonDisabled(){
        return this.busy || !this.isRequiredFieldsFilled
    }


    get isRequiredFieldsFilled() {
        for(const fieldName of this.requiredFields) {
            const subnames = fieldName.split("."); // Сложное имя поля (пр. assignment.executor)
            let obj = this.entity;
            for (let i = 0; i < subnames.length; i++) {
                const subname = subnames[i];
                const value = obj[subname];
                if (value == null) return false;
                obj = obj[subname]
            }
        }
        return true;
    }

}

export {BaseEntityCreate}