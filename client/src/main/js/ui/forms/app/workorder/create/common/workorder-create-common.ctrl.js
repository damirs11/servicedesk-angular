import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";
import {TYPEID_WORKORDER} from "../../../../../../api/entity/util/entity-type-list";

const DEADLINE_OFFSET_MS = 1000*60*60; // 1 Hour

@NGInjectClass()
class WorkorderCreateCommonController{
    // NG зависимости
    @NGInject() workorder;
    @NGInject() SD;
    @NGInject() passedParams; // Переданные параметры

    minDeadlineDate = new Date(Date.now() + DEADLINE_OFFSET_MS);

    get isChangePassed() {
        return Boolean(this.passedParams.changeId)
    }
    // Методы подгрузки данных

    async loadInititators(text) {
        const filter = {selectable:"1"};
        if (text) filter.fulltext = text;
        return this.SD.Person.list(filter);
    }

    async loadExecutors(text){
        const filter = {selectable: "1", hasAccount: ""};
        if (text) filter.fulltext = text;
        const workgroup = this.workorder.assignment.workgroup;
        if (workgroup) filter.workgroupId = workgroup.id;
        return this.SD.Person.list(filter);
    }

    async loadWorkgroups(text){
        const filter = {selectable: "1"};
        if (text) filter.fulltext = text;
        const executor = this.workorder.assignment.executor;
        if (executor) filter.personId = executor.id;
        return this.SD.Workgroup.list(filter);
    }

    async loadPriorities(text) {
        const filter = {entityTypeId: TYPEID_WORKORDER};
        if (text) filter.fulltext = text;
        return this.SD.EntityPriority.list(filter);
    }

    async loadCategories(text) {
        const filter = {entityTypeId: TYPEID_WORKORDER};
        if (text) filter.fulltext = text;
        return this.SD.EntityCategory.list(filter);
    }

    async loadFolders(text) {
        const filter = {entityTypeId: TYPEID_WORKORDER};
        if (text) filter.fulltext = text;
        return this.SD.Folder.list(filter);
    }

    async loadStatuses(text) {
        const filter = {entityTypeId: TYPEID_WORKORDER};
        if (text) filter.fulltext = text;
        return this.SD.EntityStatus.list(filter);
    }

    async loadChanges(text) {
        const filter = {selectable: "1"};
        if (text) filter.fulltext = text;
        return this.SD.Change.list(filter);
    }

}

export {WorkorderCreateCommonController as controller}