import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ChangeCreateCommonController{
    // NG зависимости
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() $state;
    @NGInject() $pageLock;

    async $onInit() {
    }



}

export {ChangeCreateCommonController as controller}