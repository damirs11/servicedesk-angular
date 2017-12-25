import {CHANGE_MESSAGE_TYPES} from "../../../../../components/widget/sd-entity-chat/chat-types";
import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ChangeCardViewController{
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";
    // NG зависимости
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() changeId;
    @NGInject() ModalAction;
    @NGInject() $state;
    @NGInject() $transitions;

    constructor(){
        this.msgTypes = CHANGE_MESSAGE_TYPES;
    }

    $onInit() {
        this.change = new this.SD.Change(this.changeId);
        this.loadStatuses();
        this.registerLeaveEditListener();
    }

    async loadStatuses() {
        this.statusList = await this.SD.EntityStatus.list({entityTypeId:this.SD.Change.$entityTypeId});
    }

    // Статус стейта, редактирование/просмотр
    editing = false;
    startEditing(){
        this.editing = true;
    }
    async saveEditing(){
        await this.change.save();
        this.editing = false;
    }
    cancelEditing(){
        this.editing = false;
        this.change.reset();
    }

    registerLeaveEditListener() {
        const fromCurrentState = {
            from: state => state.name === this.$state.current.name,
        };
        const removeExitHook = this.$transitions.onExit(fromCurrentState,async () => {
            if (!this.change || !this.change.checkModified()) return;
            const modalResult = await this.ModalAction.entityChanged();
            if (modalResult == 0) {
                await this.change.save();
                return true;
            } else if (modalResult == 1) {
                this.change.reset();
                return true;
            } else {
                return false;
            }
        });
        const onBeforeUnload = event => {
            if (!this.change || !this.change.checkModified()) return;
            console.log("onUnload");
            event.returnValue = "Вы действительно хотите покинуть страницу? Несохраненные изменения будут утеряны.";
            return event.returnValue;
        };
        window.addEventListener("beforeunload", onBeforeUnload);
        this.$scope.$on("$destroy", () => {
            removeExitHook();
            window.removeEventListener("beforeunload", onBeforeUnload)
        })
    }

    async createTestChange() {
        const parent = this.change;
        const testChange = new this.SD.Change();
        testChange.category = parent.category;
        testChange.classification = parent.classification;
        testChange.description = "Проверка создания изменений";
        testChange.subject = "Тестовое изменение";
        testChange.priority = parent.priority;
        testChange.deadline = new Date(Date.now()+2*60*60*1000);
        testChange.initiator = parent.manager;
        testChange.manager = parent.initiator;
        testChange.workgroup = parent.workgroup;
        testChange.executor = parent.manager;
        await testChange.create();
    }
}

export {ChangeCardViewController as controller}