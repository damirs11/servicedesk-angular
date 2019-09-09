import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";
import {BaseEntityCommonCreated} from "../../../entity-created/base-create.common";

@NGInjectClass()
class ProblemCreateCommonController extends BaseEntityCommonCreated {
    // NG зависимости
    @NGInject() problem;
    @NGInject() SD;
    @NGInject() $scope;

    async $onInit() {
        this.typeId = this.SD.Problem.$entityTypeId;
    }

}

export {ProblemCreateCommonController as controller}