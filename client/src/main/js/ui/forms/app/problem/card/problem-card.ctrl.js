import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";
import {CHANGE_STATUSES, APPROVAL_STATUSES} from "../../../../../api/entity/util/status-list";
import {TYPEID_PROBLEM} from "../../../../../api/entity/util/entity-type-list";

@NGInjectClass()
class ProblemCardController {
    /**
     * Занят ли контроллер. Будет отображат анимацию загрузки
     * @type {string}
     */
    busy;
    /**
     * Ошибка при загрузке
     * Или ошибка прав
     * @type {Error}
     */
    loadingError;
    /**
     * Сущность - "Проблема"
     * @type {SD.Problem}
     */
    problem;
    /**
     * Права доступа;
     * @type {SDAccessRules}
     */
    accessRules;
    /**
     * Промис загрузки "проблемы"
     */
    loadingPromise;
    /**
     * Вкладки в хидере карточки
     */
    headerTabs = [
        {name:'Просмотр',sref:'app.problem.card.view'},
        // {name:'История',sref:'app.problem.card.history',
        //     disabled:() => this.accessRules && !this.accessRules.isReadHistoryAllowed},
        // {name:'Согласование',sref:'app.problem.card.approval',
        //     disabled:() => this.accessRules && !this.canSeeApprovalPage},
        // {name:'Вложения',sref:'app.problem.card.attachments',
        //     disabled:() => this.accessRules && !this.accessRules.isReadAttachmentsAllowed},
        // {name:'Наряды',sref:'app.problem.card.workorders'},
    ];

    @NGInject() SD;
    @NGInject() problemId;
    @NGInject() Session;

    async $onInit(){
        this.loadingPromise = this.loadData();
        await this.loadingPromise;
        this.accessRules = this.problem.accessRules;
    }

    async loadData(){
        await this.$loadProblem();
        await this.$loadAccess();
        await this.$loadStatuses();
        // await this.$loadApproval();
    }

    async $loadStatuses() {
        this.statusList = await this.SD.EntityStatus.list({entityTypeId:TYPEID_PROBLEM});
    }

    // async $loadApproval() {
    //     this.approval = await this.problem.getApproval()
    // }

    get loading(){
        return this.busy === "loading";
    }

    get loadingErrorIsNotFound(){
        return this.loadingError && this.loadingError.status === 404;
    }

    get loadingErrorIsServerOffline(){
        return this.loadingError && this.loadingError.status === -1;
    }

    get loadingErrorIsCustom(){
        return !this.loadingErrorIsNotFound &&
            !this.loadingErrorIsServerOffline
        ;
    }
    get customErrorText() {
        if (this.loadingError && this.loadingError.data.text) return this.loadingError.data.text;
        return this.loadingError.toString();
    }

    async $loadAccess() {
        await this.problem.updateAccessRules();
    }

    async $loadProblem(){
        try {
            this.busy = "loading";
            this.problem = await new this.SD.Problem(this.problemId).load();
        } catch (error) {
            this.loadingError = error || true;
            throw error;
        }
    }

    // get canSeeApprovalPage() {
    //     return this.accessRules.isReadApprovalAllowed
    //         && this.approval
    // }
    //
    // get $isManager(){
    //     return this.problem.manager
    //         && (this.problem.manager.id == this.Session.user.person.id)
    // }
}

export {ProblemCardController as controller}