import {WORKORDER_MESSAGE_TYPES} from "../../../../../components/widget/sd-entity-chat/chat-types";
import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";


@NGInjectClass()
class WorkorderCardViewController{
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() workorderId;
    @NGInject() ModalAction;
    @NGInject() $state;
    @NGInject() $transitions;
    @NGInject() $pageLock;

    /**
     * Дублирование workorder.entityAccess
     * для краткости в html
     */
    entityAccess;
    /**
     * Наряд
     */
    workorder;
    /**
     * Режим редактирования
     */
    editing;


    $onInit() {
        this.msgTypes = WORKORDER_MESSAGE_TYPES;
        this.workorder = new this.SD.Workorder(this.workorderId);
        this.accessRules = this.workorder.accessRules;
        this.registerLeaveEditListener();
    }

    // Статус стейта, редактирование/просмотр
    editing = false;
    startEditing(){
        this.editing = true;
    }
    async saveEditing(){
        await this.workorder.save();
        this.editing = false;
    }
    cancelEditing(){
        this.workorder.reset();
        this.editing = false;
    }

    registerLeaveEditListener() {
        this.$pageLock(this.$scope)
            .setTitle("Несохраненные изменения")
            .setText("Внимание! В сущность были внесены изменение, сохранить их перед уходом?")
            .setCondition(() => this.editing && this.workorder.checkModified())
            .addAction("Да",async () => {
                await this.saveEditing();
                return true;
            })
            .addAction("Нет", () => {
                this.workorder.reset();
                return true;
            })
            .addAction("Отмена", () => false)
            .lock();
    }
}

export {WorkorderCardViewController as controller}