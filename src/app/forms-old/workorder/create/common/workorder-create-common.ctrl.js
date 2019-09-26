import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";
import {TYPEID_WORKORDER} from "../../../../../../api/entity/util/entity-types";
import {WORKORDER_STATUSES} from "../../../../../../api/entity/util/status-list";
import {BaseEntityCommonCreated} from "../../../entity-created/base-create.common";

const DEADLINE_OFFSET_MS = 1000*60*60; // 1 Hour

@NGInjectClass()
class WorkorderCreateCommonController extends BaseEntityCommonCreated {
    // NG зависимости
    @NGInject() workorder;
    @NGInject() SD;
    @NGInject() passedParams; // Переданные параметры
    @NGInject() $scope;

    minDeadlineDate = new Date(Date.now() + DEADLINE_OFFSET_MS);

    async $onInit() {
        this.typeId = TYPEID_WORKORDER;
    }

    changedStatus(value) {
        console.debug("status was changed: " + value);
        this.workorder.status = value;
        // Поле 'Решение' активно при статусе COMPLETE
        this.solutionEnable = this.workorder.status && this.workorder.status.id === WORKORDER_STATUSES.COMPLETE;
        if (!this.solutionEnable && this.workorder.solution) {
            this.workorder.solution = null;
        }
    }

    get isChangePassed() {
        return Boolean(this.passedParams.changeId)
    }
    get isProblemPassed() {
        return Boolean(this.passedParams.problemId)
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