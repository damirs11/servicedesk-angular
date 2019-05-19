import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";
import {SERVICECALL_STATUSES} from "../../../../../../api/entity/util/status-list";
import {EntityTypes} from "../../../../../../api/entity/util/entity-types";
import {SOURCES} from "../../../../../../api/entity/util/source-list";
import {FOLDERS} from "../../../../../../api/entity/util/folder-list";
import {PERSONS} from "../../../../../../api/entity/util/person-list";
import {SLA} from "../../../../../../api/entity/util/sla-list";

@NGInjectClass()
class ServiceCallCreateCommonController {
    // NG зависимости
    @NGInject() entity;
    @NGInject() SD;
    @NGInject() $scope;
    @NGInject() Session;

    required_fields = [];

    $onInit() {
        this.entity.initiator = this.Session.user.person; // todo проверить категорию и только тогда выставить
        this.entity.status = {id: SERVICECALL_STATUSES.REGISTERED};
        this.entity.serviceLevel = {};
        this.entity.deadline = new Date();
        this.initOnChangeHandlers();
    }

    /**
     * Устанавливает обработики на изменение значений в полях
     */
    initOnChangeHandlers() {
        this.enableWatch = {}; // объект для хранения флагов игнорирования наблюдения за значениями
        this.$scope.$watch("ctrl.entity.organization", () => {
            if (this.enableWatch.organization) {
                console.debug('organization was changed');
                this.enableWatch.caller = this.enableWatch.service = false;
                this.entity.caller = null; // удаляем заявителя
                this.checkOrgPersonService(); // проверяем значение в поле Сервис
            }
            this.enableWatch.organization = true;
        });

        this.$scope.$watch("ctrl.entity.caller", () => {
            if (this.enableWatch.caller) {
                console.debug('caller was changed');
                const caller = this.entity.caller;
                if (caller && caller.organization) { // если задан заявитель, то должна отображаться его организация
                    this.enableWatch.organization = false;
                    this.entity.organization = caller.organization;
                }
                this.checkOrgPersonService(); // проверяем значение в поле Сервис
            }
            this.enableWatch.caller = true;
        });

        this.$scope.$watch("ctrl.entity.service", async () => {
            if (this.enableWatch.service) {
                console.debug('service was changed');
                // todo определить приоритет по умолчанию
                this.refreshDeadline();
                this.changedSLA();
            }
            this.enableWatch.service = true;
        });
        this.$scope.$watch("ctrl.entity.source", async () => {
            if (this.enableWatch.source) {
                console.debug('source was changed');
                this.changedSource();
            }
            this.enableWatch.source = true;
        });

        this.$scope.$watch("ctrl.entity.priority", async () => {
            if (this.enableWatch.priority) {
                console.debug('priority was changed');
                this.refreshDeadline();
            }
            this.enableWatch.priority = true;
        });
        this.$scope.$watch("ctrl.entity.folder", async () => {
            if (this.enableWatch.folder) {
                console.debug('folder was changed');
                this.changedFolder();
            }
            this.enableWatch.folder = true;
        });
    }

    get isParentBusy() {
        return this.$scope.$parent.ctrl.busy;
    }

    async loadOrganizations(text, fromUI) {
        const filter = {
            paging: "1;20",
            name_like: text
        };
        return this.SD.Organization.list(filter);
    }
    async changedFolder() {
        var folder = this.entity.folder;
        if (folder && folder.id === FOLDERS.ALCOA) {
            this.entity.initiator = await new this.SD.Person(PERSONS.ALCOA_HELPDESK).load();
        }
    }
    async changedSLA() {
        var sla = this.entity.service.sla;
        if (sla && sla.id === SLA.SIBUR && this.entity.status.id !== SERVICECALL_STATUSES.CLOSED) {
            this.entity.initiator = await new this.SD.Person(PERSONS.BYKOV_A_A).load();
        }
    }

    async changedSource() {
        if (this.entity.source.id === SOURCES.EMAIL) {
            //Если source === email делаем поле emailDate обязательным
            this.entity.emailDate = new Date(new Date().getTime());
            this.required_fields.push("emailDate")
        } if (this.entity.source.id === SOURCES.EXTERNAL_SYSTEM) {
            this.required_fields.push("extId");
        } else {
            //Иначе удаляем из массива обязательных полей
            this.entity.emailDate = undefined;
            var that = this;
            this.required_fields.filter(function (value, index) {
                if (value === "emailDate" || value === "extId") {
                    delete that.required_fields[index];
                }
            });
        }
    }
    async loadCallers(text) {
        const filter = {
            paging: "1;20",
            fullname_like: text
        };
        const organization = this.entity.organization;
        if (organization) {
            filter.organization = organization.id;
        }
        const callers = await this.SD.Person.list(filter);
        callers.sort((a, b) => {
            return (a && b && a.fullName && a.fullName.localeCompare(b.fullName));
        });
        return callers;
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
            }).sort((a, b) => {
                return (a && b && a.name && a.name.localeCompare(b.name));
            });
        }
    }

    async setEmailDate() {

    }

    async loadSLA() {
        const organization = this.entity.organization;
        if (organization) {
            const now = new Date();
            const filter = {
                validTo_after: now,
                validFrom_before: now,
                status: 281478725107716 // статус "В работе"
            };
            filter.organizationId = organization.id;
            const caller = this.entity.caller;
            if (caller) {
                filter.personId = caller.id;
            }
            return this.SD.ServiceLevelAgreement.list(filter);
        }
    }

    /**
     * Удаляем сервис, если он не соответствует новому значению организации. Иначе выбранный сервис остается.
     */
    async checkOrgPersonService() {
        if (!this.entity.organization || !this.entity.service) {
            this.entity.service = null;
        } else {
            const services = await this.loadServices();
            if (services.indexOf(this.entity.service) === -1) {
                this.entity.service = null;
            }
        }
    }

    /**
     * Обновляет крайний срок в зависимости от приоритета и SLA
     */
    async refreshDeadline() {
        const service = this.entity.service;
        const priority = this.entity.priority;
        if (service && priority && service.sla && service.sla.serviceLevel) {
            this.entity.serviceLevel = await new this.SD.ServiceLevel(service.sla.serviceLevel.id).load(); // todo нужно ли еще фильтровать по blocked = false?
            const impactSetting = this.entity.serviceLevel.impactSettingList.find((element, index, array) => {
                return element.priority.id === priority.id;
            });
            if (impactSetting) {
                const entityId = EntityTypes.entity;
                this.entity.serviceLevelPriority = impactSetting.serviceLevelPriority;
                const durations = await this.SD.ServiceLevelPriorityDuration.list({
                    entityId,
                    serviceLevelPriority: impactSetting.serviceLevelPriority.id
                });
                if (Array.isArray(durations) && durations.length > 0) {
                    const serviceLevelPriorityDuration = durations[0];
                    const deadline = new Date(new Date().getTime() + serviceLevelPriorityDuration.maximumDuration);
                    console.log("new deadline is " + deadline);
                    this.entity.deadline = deadline;
                    return;
                }
            }
        }
        this.entity.serviceLevel = null;
        this.entity.serviceLevelPriority = null;
        this.entity.deadline = null;
    }


    async loadInititators(text) {
        const filter = {paging: "1;20"};
        if (text) filter.fullname_like = text;
        return this.SD.Person.list(filter);
    }

    async loadExecutors(text) {
        const filter = {selectable: "1", hasAccount: ""};
        if (text) filter.fullname_like = text;
        const workgroup = this.entity.assignment.workgroup;
        if (workgroup) filter.workgroupId = workgroup.id;
        return this.SD.Person.list(filter);
    }

    async loadWorkgroups(text) {
        const filter = {selectable: "1"};
        if (text) filter.name_like = text;
        const executor = this.entity.assignment.executor;
        if (executor) filter.personId = executor.id;
        return this.SD.Workgroup.list(filter);
    }

    async loadStatuses(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityStatus.list(filter);
    }

    /**
     * Загрузка списка значений поля "Источник"
     * @param text
     * @returns {Promise<*>}
     */
    async loadSources(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.Source.list(filter);
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