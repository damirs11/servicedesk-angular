import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";
import {BaseEntityCreate} from "../../entity-created/base-create";

@NGInjectClass()
class ChangeCreateController extends BaseEntityCreate {
    // NG зависимости
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() $state;
    @NGInject() $pageLock;
    @NGInject() ModalAction;
    @NGInject() passedParams;
    @NGInject() change; // Новое изменение - внедряется зависимостью.

    async $onInit(){
        this.stateCreated = "app.change.create.common";
        this.stateView = "app.change.card.view";
        this.stateList = "app.change.list";
        this.entity = this.change;
        super.$onInit();
    }

    async _callCreatedFunc() {
        const createdChange = await this.entity.create();
        await this.ModalAction.changeCreated(this.$scope,{change:createdChange});
        this.$state.go(this.stateView, {changeId: createdChange.id});
    }

}

export {ChangeCreateController as controller}