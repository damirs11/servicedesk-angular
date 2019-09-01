import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";
import {SERVICECALL_STATUSES} from "../../../../../../api/entity/util/status-list";
import {EntityTypes} from "../../../../../../api/entity/util/entity-types";
import {SOURCES} from "../../../../../../api/entity/util/source-list";
import {FOLDERS} from "../../../../../../api/entity/util/folder-list";
import {PERSONS} from "../../../../../../api/entity/util/person-list";
import {SLA} from "../../../../../../api/entity/util/sla-list";
import {CLASSIFICATIONS} from "../../../../../../api/entity/util/classification-list";
import {PERSON_CATEGORIES} from "../../../../../../api/entity/util/category-list";
import {BaseEntityCommonCreated} from "../../../entity-created/base-create.common";

@NGInjectClass()
class ServiceCallCreateCommonController extends BaseEntityCommonCreated {

    @NGInject() entity;
    @NGInject() Session;
    @NGInject() $scope;
    @NGInject() SD;

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
        this.typeId = this.SD.ServiceCall.$entityTypeId;
    }

    async changedOrganization(value) {
        console.debug('organization was changed: ' + value);
        this.entity.organization = value;
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
        await this.checkOrgPersonService(); // проверяем значение в поле Сервис
    }

    async changedCaller(value) {
        console.debug('caller was changed: ' + value);
        const caller = this.entity.caller = value;
        if (caller && caller.organization && (!this.entity.organization || this.entity.organization.id === caller.organization.id)) {//todo нужно ли второе условие ?
            // Если задан заявитель и у него есть организация и выбранная организация = null или совпадает с организацией заявителя
            this.entity.organization = caller.organization;
        }
        await this.checkOrgPersonService(); // проверяем значение в поле Сервис
    }

    async changedService(value) {
        console.debug('service was changed: ' + value);
        this.entity.service = value;
        if (!this.entity.service) return;
        var sla = this.entity.service.sla;
        if (!sla || !sla.id) return;
        if (sla.id === SLA.SIBUR && this.entity.status.id !== SERVICECALL_STATUSES.CLOSED) {
            this.entity.initiator = await new this.SD.Person(PERSONS.BYKOV_A_A).load();
        }
        await this.refreshDeadline();
        await this.refreshPriority();
    }

    async changedSource(value) {
        console.debug('source was changed: ' + value);
        this.entity.source = value;
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

    async changedPriority(value) {
        console.debug('priority was changed: ' + value);
        this.entity.priority = value;
        await this.refreshDeadline();
    }

    async changedFolder(value) {
        console.debug('folder was changed: ' + value);
        const folder = this.entity.folder = value;
        if (folder && folder.id === FOLDERS.ALCOA) {
            this.entity.initiator = await new this.SD.Person(PERSONS.ALCOA_HELPDESK).load();
        }
    }

    async changedNewDeadline(value) {
        console.debug('newDeadline changed: ' + value);
        this.entity.newDeadline = value;
        await this.newDeadlineChanged();
    }

    async changedNewDeadlineReason(value) {
        console.debug('newDeadlineReason changed: ' + value);
        this.entity.newDeadlineReason = value;
        await this.newDeadlineChanged();
    }

    changedRegistrationError(value) {
        console.debug('registrationError changed: ' + value);
        this.entity.registrationError = value;
        //Если registrationError == true, то заблокировать редактирование
        if (this.entity.registrationError) {
            this.disabled_fields.push("registrationError");
        }
    }

    async changedFrequentlyAskedQuestion(value) {
        console.debug('frequentlyAskedQuestion changed: ' + value);
        this.entity.frequentlyAskedQuestion = value;
        await this.frequentlyAskedQuestionChanged();
    }

    async changedWorkgroup(value) {
        console.debug('assignment.workgroup changed: ' + value);
        this.entity.assignment.workgroup = value;
        if (this.entity.assignment.workgroup && this.entity.assignment.workgroup.groupManager) {
            this.entity.assignment.executor = await new this.SD.Person(this.entity.assignment.workgroup.groupManager.id).load();
        } else if (!this.entity.assignment.workgroup) {
            this.entity.assignment.executor = null;
        }
    }

    async changedExecutor(value) {
        console.debug('assignment.executor changed: ' + value);
        this.entity.assignment.executor = value;
        this.entity.executorHead = null;
        if (this.entity.assignment.executor) {
            let groups = await this.loadWorkgroups();
            if (groups && groups.length === 1) {
                this.entity.assignment.workgroup = groups[0];
            }
        }
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

    async loadCallers(text) {
        const filter = {
            paging: "1;20",
            fullname_like: text
        };
        const organization = this.entity.organization;
        if (organization) {
            filter.organization = organization.id;
            filter.paging = undefined;
            const callers = await this.SD.Person.list(filter);
            callers.sort((a, b) => {
                return (a && b && a.fullName && a.fullName.localeCompare(b.fullName));
            });
            return callers;
        }
        return null;
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

    /**
     * Загрузка списка значений поля "Источник"
     * @param text
     * @returns {Promise<*>}
     */
    async loadSources(text) {
        const filter = {entityTypeId: this.typeId};
        if (text) filter.fulltext = text;
        return this.SD.Source.list(filter);
    }

    /**
     * Загрузка значений поля entityCode6
     * @param text
     * @returns {Promise<*>}
     */
    async loadEntityCode6(text) {
        const filter = {entityTypeId: this.typeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityCode6.list(filter);
    }

    async loadFaq(text) {
        const filter = {entityTypeId: this.typeId};
        if (text) filter.fulltext = text;
        return this.SD.FAQ.list(filter);
    }

    async loadClassifications(text) {
        const filter = {entityTypeId: this.typeId};
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

}

export {ServiceCallCreateCommonController as controller};