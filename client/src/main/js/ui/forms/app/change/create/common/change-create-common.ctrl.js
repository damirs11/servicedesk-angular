import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

const DEADLINE_OFFSET_MS = 1000*60*60; // 1 Hour

@NGInjectClass()
class ChangeCreateCommonController{
    // NG зависимости
    @NGInject() change;
    @NGInject() SD;

    minDeadlineDate = new Date(Date.now() + DEADLINE_OFFSET_MS);

    async $onInit() {
    }

    async loadInititators(text) {
        const filter = {};
        if (text) filter.fulltext = text;
        return this.SD.Person.list(filter);
    }

    async loadManagers(text) {
        const filter = {};
        if (text) filter.fulltext = text;
        return this.SD.Person.list(filter);
    }

    async loadExecutors(text){
        const filter = {};
        if (text) filter.fulltext = text;
        const workgroup = this.change.assignment.workgroup;
        if (workgroup) filter.workgroupId = workgroup.id;
        return this.SD.Person.list(filter);
    }

    async loadWorkgroups(text){
        const filter = {};
        if (text) filter.fulltext = text;
        const executor = this.change.assignment.executor;
        if (executor) filter.personId = executor.id;
        return this.SD.Workgroup.list(filter);
    }

    async loadStatuses(text) {
        const filter = {entityTypeId: this.SD.Change.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityStatus.list(filter);
    }

    async loadPriorities(text) {
        const filter = {entityTypeId: this.SD.Change.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityPriority.list(filter);
    }

    async loadCategories(text) {
        const filter = {entityTypeId: this.SD.Change.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityCategory.list(filter);
    }

    async loadClassifications(text) {
        const filter = {entityTypeId: this.SD.Change.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityClassification.list(filter);
    }


}

export {ChangeCreateCommonController as controller}