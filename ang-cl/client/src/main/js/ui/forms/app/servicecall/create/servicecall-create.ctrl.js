import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";
import {BaseEntityCreate} from "../../entity-created/base-create";

@NGInjectClass()
class ServiceCallCreateController extends BaseEntityCreate {
    // NG зависимости
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() $state;
    @NGInject() $pageLock;
    @NGInject() ModalAction;
    @NGInject() passedParams;
    @NGInject() entity; // Новая заявка - внедряется зависимостью.

    async $onInit(){
        this.stateCreated = "app.serviceсall.create.common";
        this.stateView = "app.serviceсall.card.view";
        this.stateList = "app.servicecall.list";
        super.$onInit();
    }

    async create(){
        if (this.busy) return;
        this.busy = true;
        this.errorCreating = false;
        try {

        } catch (e) {
            this.errorCreating = true;
            this.busy = false;
            throw e;
        }
        this.busy = false;
    }

    async _callCreatedFunc() {
        //todo временно удаляем "крайний срок" из заявки, так как поле readonly при создании
        delete this.entity.deadline;

        const createdEntity = await this.entity.create();
        await this.ModalAction.serviceCallCreated(this.$scope, {entity:createdEntity});
        this.$state.go(this.stateView, {entityId: createdEntity.id});
    }

}

export {ServiceCallCreateController as controller}