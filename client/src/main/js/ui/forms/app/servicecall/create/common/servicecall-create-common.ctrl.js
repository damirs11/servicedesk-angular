import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";
import {SERVICECALL_STATUSES} from "../../../../../../api/entity/util/status-list";
import {EntityTypes} from "../../../../../../api/entity/util/entity-types";
import {SOURCES} from "../../../../../../api/entity/util/source-list";
import {FOLDERS} from "../../../../../../api/entity/util/folder-list";
import {PERSONS} from "../../../../../../api/entity/util/person-list";
import {SLA} from "../../../../../../api/entity/util/sla-list";
import {CLASSIFICATIONS} from "../../../../../../api/entity/util/classification-list";
import {PERSON_CATEGORIES} from "../../../../../../api/entity/util/category-list";

@NGInjectClass()
class ServiceCallCreateCommonController {
    // NG зависимости
    @NGInject() entity;
    @NGInject() SD;
    @NGInject() $scope;
    @NGInject() Session;

    required_fields = [];
    disabled_fields = [];

    async $onInit() {
        let current_person = this.Session.user.person;
        if (current_person.category.id === PERSON_CATEGORIES.CALLER) {
            this.entity.caller = this.Session.user.person;
            this.entity.source = await new this.SD.Source(SOURCES.WEB).load();
        }
        this.entity.status = {id: SERVICECALL_STATUSES.REGISTERED};
        this.entity.serviceLevel = {};
        this.entity.deadline = new Date();
        this.initOnChangeHandlers();
    }

    /**
     * Устанавливает обработики на изменение значений в полях
     */
    initOnChangeHandlers() {
        this.enableWatch = {caller: true}; // объект для хранения флагов игнорирования наблюдения за значениями
        this.$scope.$watch("ctrl.entity.organization", () => {
            if (this.enableWatch.organization) {
                console.debug('organization was changed');
                //Если неопределена организация или заявитель или организация заявителя != выбранной организации
                if (this.entity.organization && this.entity.caller && this.entity.organization.id !== this.entity.caller.organization.id) {
                    this.entity.caller = null; // удаляем заявителя
                }
                if (this.entity.organization) {
                    //Если выбрана организация, то проставляем папку этой организации в поле папка
                    this.entity.folder = this.entity.organization.folder;
                } else {
                    //Если очистили организацию обнуляем папку и заявителя
                    this.entity.folder = null;
                    this.entity.caller = null;
                }
                this.checkOrgPersonService(); // проверяем значение в поле Сервис
            }
            this.enableWatch.organization = true;
        });

        this.$scope.$watch("ctrl.entity.caller", () => {
            if (this.enableWatch.caller) {
                console.debug('caller was changed');
                const caller = this.entity.caller;
                if (caller && caller.organization && (!this.entity.organization || this.entity.organization.id === caller.organization.id)) {//todo нужно ли второе условие ?
                    // Если задан заявитель и у него есть организация и выбранная организация = null или совпадает с организацией заявителя
                    this.entity.organization = caller.organization;
                }
                this.checkOrgPersonService(); // проверяем значение в поле Сервис
            }
            this.enableWatch.caller = true;
        });

        this.$scope.$watch("ctrl.entity.service", async () => {
            console.debug('service was changed');
            let service = this.entity.service;
            let slaId = service && service.sla ? service.sla.id : null;
            this.refreshDeadline();
            this.changedSLA();
            this.refreshPriority();

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
        this.$scope.$watch("ctrl.entity.newDeadline", async () => {
            if (this.enableWatch.newDeadline) {
                console.debug('newDeadline changed');
                this.newDeadlineChanged();
            }
            this.enableWatch.newDeadline = true;
        });
        this.$scope.$watch("ctrl.entity.newDeadlineReason", async () => {
            if (this.enableWatch.newDeadlineReason) {
                console.debug('newDeadlineReason changed');
                this.newDeadlineChanged();
            }
            this.enableWatch.newDeadlineReason = true;
        });
        this.$scope.$watch("ctrl.entity.registrationError", async () => {
            //Отработать только при инициализации
            if (!this.enableWatch.registrationError) {
                console.debug('registrationError changed');
                //Если registrationError == true, то заблокировать редактирование
                if (this.entity.registrationError) {
                    this.disabled_fields.push("registrationError");
                }
                this.enableWatch.registrationError = true;
            }
        });
        this.$scope.$watch("ctrl.entity.frequentlyAskedQuestion", async () => {
            console.debug('frequentlyAskedQuestion changed');
            this.frequentlyAskedQuestionChanged();
        });
        this.$scope.$watch("ctrl.entity.assignment.workgroup", async () => {
            if (this.enableWatch.assignmentWorkgroup) {
                console.debug('assignment.workgroup changed');
                if (this.entity.assignment.workgroup && this.entity.assignment.workgroup.groupManager) {
                    this.entity.assignment.executor = await new this.SD.Person(this.entity.assignment.workgroup.groupManager.id).load();
                } else if (!this.entity.assignment.workgroup) {
                    this.entity.assignment.executor = null;
                }
            }
            this.enableWatch.assignmentWorkgroup = true;
        });
        this.$scope.$watch("ctrl.entity.assignment.executor", async () => {
            if (this.enableWatch.assignmentExecutor) {
                console.debug('assignment.executor changed');
                this.entity.executorHead = null;
                if (this.entity.assignment.executor) {
                    let groups = await this.loadWorkgroups();
                    if (groups && groups.length === 1) {
                        this.entity.assignment.workgroup = groups[0];
                    }
                }
            }
            this.enableWatch.assignmentExecutor = true;
        });
    }

    get isParentBusy() {
        return this.$scope.$parent.ctrl.busy;
    }

    isDisabled(fieldName) {
        return this.disabled_fields.includes(fieldName);
    }

    async loadOrganizations(text, fromUI) {
        const filter = {
            paging: "1;20",
            name_like: text
        };
        return this.SD.Organization.list(filter);
    }

    /**
     * Изменение "newDeadlineReason" или "newDeadline",
     * если одно из полей задано, то оба становятся обязательными.
     * @returns {Promise<void>}
     */
    async newDeadlineChanged() {
        let newDeadlineField = "newDeadline";
        let newDeadlineReasonField = "newDeadlineReason";
        let fieldsAlreadyRequired = this.required_fields.includes(newDeadlineField) && this.required_fields.includes(newDeadlineReasonField);
        if ((this.entity.newDeadline || this.entity.newDeadlineReason) && !fieldsAlreadyRequired) {
            //Если заполнено поле newDeadline или newDeadlineReason и поля небыли добавлены в обязательные, то добавить
            this.required_fields.push(newDeadlineField);
            this.required_fields.push(newDeadlineReasonField);
        } else if (!this.entity.newDeadline && !this.entity.newDeadlineReason && fieldsAlreadyRequired) {
            //Если ни одно, ни другое поле не задано, то удалить из обязательных
            let that = this;
            this.required_fields.filter(function (value, index) {
                if (value === newDeadlineField || value === newDeadlineReasonField) {
                    delete that.required_fields[index];
                }
            });
        }
    }

    async frequentlyAskedQuestionChanged() {
        let faqEnabled = this.entity.frequentlyAskedQuestion;
        if (faqEnabled) {
            this.required_fields.push("faq");
        } else {
            let that = this;
            this.required_fields.filter(function (value, index) {
                if (value === "faq") {
                    delete that.required_fields[index];
                }
            });
        }
    }

    async changedFolder() {
        var folder = this.entity.folder;
        if (folder && folder.id === FOLDERS.ALCOA) {
            this.entity.initiator = await new this.SD.Person(PERSONS.ALCOA_HELPDESK).load();
        }
    }

    async changedSLA() {
        if (!this.entity.service) return;
        var sla = this.entity.service.sla;
        if (!sla || !sla.id) return;
        if (sla.id === SLA.SIBUR && this.entity.status.id !== SERVICECALL_STATUSES.CLOSED) {
            this.entity.initiator = await new this.SD.Person(PERSONS.BYKOV_A_A).load();
        }
    }

    async refreshPriority() {
        if (!this.entity.service || !this.entity.service.sla.defaultPriority) return;
        let priority = await this.loadPriorities();
        let that = this;
        priority.filter(function (val) {
            if (val.order === that.entity.service.sla.defaultPriority.order) {
                that.entity.priority = val;
            }
        });
    }

    async changedSource() {
        if (this.entity.source.id === SOURCES.EMAIL) {
            //Если source === email делаем поле emailDate обязательным
            this.entity.emailDate = new Date(new Date().getTime());
            this.required_fields.push("emailDate")
        }
        if (this.entity.source.id === SOURCES.EXTERNAL_SYSTEM) {
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
            filter.paging = undefined;
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
        const filter = {blocked: false, hasAccount: ""};
        if (text) filter.fullname_like = text;
        const workgroup = this.entity.assignment.workgroup;
        if (workgroup) filter.workgroupId = workgroup.id;
        return this.SD.Person.list(filter);
    }

    async loadExecutorHead(text) {
        const filter = {blocked: false, hasAccount: ""};
        if (text) filter.fullname_like = text;
        return this.SD.Person.list(filter);
    }

    async loadWorkgroups(text) {
        const filter = {blocked: false};
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

    /**
     * Загрузка значений поля entityCode6
     * @param text
     * @returns {Promise<*>}
     */
    async loadEntityCode6(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityCode6.list(filter);
    }

    async loadFaq(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.FAQ.list(filter);
    }

    async loadCategories(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityCategory.list(filter);
    }

    async loadClassifications(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        if (this.entity && this.entity.service && this.entity.service.sla && this.entity.service.sla.id) {
            let sla = this.entity.service.sla;
            if ([SLA.TRANS_KIS_EHD, SLA.IVI, SLA.TRANS_KIS_EATD, SLA.TRANS_AIS_EAD, SLA.TRANS_MUZ_EDO].includes(sla.id)) {
                filter.parent = CLASSIFICATIONS.PARENT_CLASSIFICATION_1384_1385_1387_1388;
            } else if ([SLA.SLA_167, SLA.SLA_1088].includes(sla.id)) {
                filter.parent = CLASSIFICATIONS.PARENT_CLASSIFICATION_167_1088;
            } else if ([SLA.SLA_1090].includes(sla.id)) {
                filter.parent = CLASSIFICATIONS.PARENT_CLASSIFICATION_1066_1090_1394;
            }
        }
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