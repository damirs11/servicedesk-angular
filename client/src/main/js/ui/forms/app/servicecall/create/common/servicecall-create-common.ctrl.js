import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

const DEADLINE_OFFSET_MS = 1000*60*60; // 1 Hour

@NGInjectClass()
class ServiceCallCreateCommonController{
    // NG зависимости
    @NGInject() serviceCall;
    @NGInject() SD;
    @NGInject() $scope;

    minDeadlineDate = new Date(Date.now() + DEADLINE_OFFSET_MS);

    $onInit() {
        this.serviceCall.slaName = null;
        this.serviceCall.initiator = SD.user.person;
        
        this.enableWatch = {}; // объект для хранения флагов игнорирования наблюдения за значениями
        this.$scope.$watch("ctrl.serviceCall.organization", () => {
            if (this.enableWatch.organization) {
                console.debug('organization was changed');
                this.enableWatch.caller = false;
                this.serviceCall.caller = null;
                this.checkOrgPersonService();

            }
            this.enableWatch.organization = true;
        });

        this.$scope.$watch("ctrl.serviceCall.caller", () => {
            if (this.enableWatch.caller) {
                console.debug('caller was changed');
                const caller = this.serviceCall.caller;
                if (caller && caller.organization) { // если задан заявитель, то должна отображаться его организация
                    this.enableWatch.organization = false;
                    this.serviceCall.organization = caller.organization;
                }
                this.checkOrgPersonService();
            }
            this.enableWatch.caller = true;
        });

        this.$scope.$watch("ctrl.serviceCall.service", () => {
            if (this.enableWatch.service) {
                console.debug('service was changed');
                const service = this.serviceCall.service;
                this.serviceCall.slaName = service && service.sla ? service.sla.name : null; // обновляем наименование SLA
            }
            this.enableWatch.service = true;
        });
    }

    get isParentBusy(){
        return this.$scope.$parent.ctrl.busy;
    }

    async loadOrganizations(text, fromUI) {
        const filter = {
            paging: "1;20",
            name_like: text
        };
        return this.SD.Organization.list(filter);
    }

    async loadCallers(text) {
        const filter = {
            paging: "1;20",
            name_like: text
        };
        const organization = this.serviceCall.organization;
        if (organization) {
            filter.organization = organization.id;
        }
        return this.SD.Person.list(filter);
    }

    async loadServices(text) {
        const SLAs = await this.loadSLA();
        if (SLAs) {
            return SLAs.filter(sla => {
                return !text || (sla.service && sla.service.name && sla.service.name.toLowerCase().indexOf(text.toLowerCase()) !== -1); // отфильтровать по text
            }).map(sla => {
                const service = sla.service; // выдергиваем из SLA услуги
                service.sla = sla;
                return service;
            }).filter((service, index, services) => {
                return services.indexOf(service) === index; // фильтруем уникальные
            });
        }
    }

    async loadSLA() {
        const organization = this.serviceCall.organization;
        if (organization) {
            const now = new Date();
            const filter = {
                validTo_after: now,
                validFrom_before: now,
                status: 281478725107716 // статус "В работе"
            };
            filter.organization = organization.id;
            const caller = this.serviceCall.caller;
            if (caller) {
                filter.personId = caller.id;
            }
            return this.SD.ServiceLevelAgreement.list(filter);
        }
    }

    async checkOrgPersonService() {
        const organization = this.serviceCall.organization;
        if (!organization) {
            this.serviceCall.service = null;
        } else {
            const services = await this.loadServices();
            if (services.indexOf(this.serviceCall.service) === -1) {
                this.serviceCall.service = null;
            }
        }
    }

    async loadInititators(text) {
        const filter = {paging:"1;20"};
        if (text) filter.fullname_like = text;
        return this.SD.Person.list(filter);
    }

    async loadExecutors(text){
        const filter = {selectable:"1",hasAccount: ""};
        if (text) filter.fullname_like = text;
        const workgroup = this.serviceCall.assignment.workgroup;
        if (workgroup) filter.workgroupId = workgroup.id;
        return this.SD.Person.list(filter);
    }

    async loadWorkgroups(text){
        const filter = {selectable:"1"};
        if (text) filter.name_like = text;
        const executor = this.serviceCall.assignment.executor;
        if (executor) filter.personId = executor.id;
        return this.SD.Workgroup.list(filter);
    }

    async loadStatuses(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityStatus.list(filter);
    }

    async loadPriorities(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityPriority.list(filter);
    }

    async loadCategories(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityCategory.list(filter);
    }

    async loadClassifications(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityClassification.list(filter);
    }

    async loadFolders(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.Folder.list(filter);
    }

    async loadConfigurationItems(text) {
        const filter = {};
        if (text) filter.searchCode_like = text;
        return this.SD.ConfigurationItem.list(filter);
    }

}

export {ServiceCallCreateCommonController as controller};