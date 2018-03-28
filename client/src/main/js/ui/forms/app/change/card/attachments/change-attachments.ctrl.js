import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ChangeCardAttachmentsController{
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() changeId;
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";

    $onInit(){
        this.change = new this.SD.Change(this.changeId)
    }
}

export {ChangeCardAttachmentsController as controller}