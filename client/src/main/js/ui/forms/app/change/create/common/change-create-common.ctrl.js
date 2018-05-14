import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

const DEADLINE_OFFSET_MS = 1000*60*60; // 1 Hour

@NGInjectClass()
class ChangeCreateCommonController{
    // NG зависимости
    @NGInject() change;
    @NGInject() SD;
    @NGInject() $scope;

    minDeadlineDate = new Date(Date.now() + DEADLINE_OFFSET_MS);

    get isParentBusy(){
        return this.$scope.$parent.ctrl.busy
    }

    async loadInititators(text) {
        const filter = {selectable:"1"};
        if (text) filter.fullname_like = text;
        return this.SD.Person.list(filter);
    }

    async loadManagers(text) {
        const filter = {selectable:"1"};
        if (text) filter.fullname_like = text;
        return this.SD.Person.list(filter);
    }

    async loadExecutors(text){
        const filter = {selectable:"1",hasAccount: ""};
        if (text) filter.fullname_like = text;
        const workgroup = this.change.assignment.workgroup;
        if (workgroup) filter.workgroupId = workgroup.id;
        return this.SD.Person.list(filter);
    }

    async loadWorkgroups(text){
        const filter = {selectable:"1"};
        if (text) filter.name_like = text;
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

    async loadSystems(text) {
        const filter = {entityTypeId: this.SD.Change.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityCode1.list(filter);
    }

    async loadFolders(text) {
        const filter = {entityTypeId: this.SD.Change.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.Folder.list(filter);
    }

    async loadConfigurationItems(text) {
        const filter = {};
        if (text) filter.searchCode_like = text;
        return this.SD.ConfigurationItem.list(filter);
    }

}

export {ChangeCreateCommonController as controller}