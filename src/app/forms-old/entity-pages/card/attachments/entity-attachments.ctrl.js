import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

/**
 * Страница вложений для сущности.
 * Обязательные resolve параметры:
 * @param getEntity {function} - функция, которая венрнет сущность.
 */

@NGInjectClass()
class EntityCardAttachmentsController{
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() getEntity;

    $onInit(){
        this.entity = this.getEntity();
        this.accessRules = this.entity.accessRules;
    }

    get isReadAttachmentsAllowed(){
        return this.accessRules.isReadAttachmentsAllowed;
    }
}

export {EntityCardAttachmentsController as controller}