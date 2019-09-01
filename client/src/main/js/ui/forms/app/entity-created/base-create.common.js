const DEADLINE_OFFSET_MS = 1000*60*60; // 1 Hour

class BaseEntityCommonCreated {

    minDeadlineDate = new Date(Date.now() + DEADLINE_OFFSET_MS);

    async loadCategories(text) {
        const filter = {entityTypeId: this.typeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityCategory.list(filter);
    }

    async loadClassifications(text) {
        const filter = {entityTypeId: this.typeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityClassification.list(filter);
    }

    async loadConfigurationItems(text) {
        const filter = {};
        if (text) filter.searchCode_like = text;
        return this.SD.ConfigurationItem.list(filter);
    }

    async loadExecutors(text){
        const filter = {selectable:"1",hasAccount: ""};
        if (text) filter.fullname_like = text;
        const workgroup = this.change.assignment.workgroup;
        if (workgroup) filter.workgroupId = workgroup.id;
        return this.SD.Person.list(filter);
    }

    async loadFolders(text) {
        const filter = {entityTypeId: this.typeId};
        if (text) filter.fulltext = text;
        return this.SD.Folder.list(filter);
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

    async loadPriorities(text) {
        const filter = {entityTypeId: this.typeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityPriority.list(filter);
    }

    async loadStatuses(text) {
        const filter = {entityTypeId: this.typeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityStatus.list(filter);
    }

    async loadSystems(text) {
        const filter = {entityTypeId: this.typeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityCode1.list(filter);
    }

    async loadWorkgroups(text){
        const filter = {selectable:"1"};
        if (text) filter.name_like = text;
        const executor = this.change.assignment.executor;
        if (executor) filter.personId = executor.id;
        return this.SD.Workgroup.list(filter);
    }

    get isParentBusy(){
        return this.$scope.$parent.ctrl.busy
    }
}

export {BaseEntityCommonCreated}