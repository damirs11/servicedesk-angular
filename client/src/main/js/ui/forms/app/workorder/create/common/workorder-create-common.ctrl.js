import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";
import {TYPEID_WORKORDER} from "../../../../../../api/entity/util/entity-type-list";

const DEADLINE_OFFSET_MS = 1000*60*60; // 1 Hour

@NGInjectClass()
class WorkorderCreateCommonController{
    // NG зависимости
    @NGInject() workorder;
    @NGInject() SD;
    @NGInject() passedParams; // Переданные параметры
    @NGInject() $scope;

    minDeadlineDate = new Date(Date.now() + DEADLINE_OFFSET_MS);

    get isChangePassed() {
        return Boolean(this.passedParams.changeId)
    }
    get isProblemPassed() {
        return Boolean(this.passedParams.problemId)
    }
    // Методы подгрузки данных

    get isParentBusy(){
        return this.$scope.$parent.ctrl.busy
    }

    async loadInititators(text) {
        const filter = {selectable:"1"};
        if (text) filter.fullname_like = text;
        return this.SD.Person.list(filter);
    }

    async loadExecutors(text){
        const filter = {selectable: "1", hasAccount: ""};
        if (text) filter.fullname_like = text;
        const workgroup = this.workorder.assignment.workgroup;
        if (workgroup) filter.workgroupId = workgroup.id;
        return this.SD.Person.list(filter);
    }

    async loadWorkgroups(text){
        const filter = {selectable: "1"};
        if (text) filter.name_like = text;
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
        const filter = {};
        if (text) filter.no = text;
        return this.SD.Change.list(filter);
    }

    async loadProblems(text) {
        const filter = {};
        if (text) filter.no = text;
        return this.SD.Problem.list(filter);
    }

}

export {WorkorderCreateCommonController as controller}