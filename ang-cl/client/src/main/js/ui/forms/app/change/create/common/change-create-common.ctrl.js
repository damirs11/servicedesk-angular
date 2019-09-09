import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";
import {BaseEntityCommonCreated} from "../../../entity-created/base-create.common";

@NGInjectClass()
class ChangeCreateCommonController extends BaseEntityCommonCreated {
    // NG зависимости
    @NGInject() change;
    @NGInject() SD;
    @NGInject() $scope;

    async $onInit() {
        this.typeId = this.SD.Change.$entityTypeId;
    }

}

export {ChangeCreateCommonController as controller}