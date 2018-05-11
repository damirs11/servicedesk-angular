import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class WorkorderCardAttachmentsController{
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() workorderId;
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";

    $onInit(){
        this.workorder = new this.SD.Workorder(this.workorderId)
    }
}

export {WorkorderCardAttachmentsController as controller}