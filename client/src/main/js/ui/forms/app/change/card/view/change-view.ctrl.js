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
    @NGInject() $pageLock;

    /**
     * Дублирование change.entityAccess
     * для краткости в html
     */
    entityAccess;

    constructor(){
        this.msgTypes = CHANGE_MESSAGE_TYPES;
    }

    $onInit() {
        this.change = new this.SD.Change(this.changeId);
        this.entityAccess = this.change.entityAccess;
        this.registerLeaveEditListener();
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
        this.change.reset();
        this.editing = false;
    }

    registerLeaveEditListener() {
        this.$pageLock(this.$scope)
            .setTitle("Несохраненные изменения")
            .setText("Внимание! В сущность были внесены изменение, сохранить их перед уходом?")
            .setCondition(() => this.change.checkModified())
            .addAction("Да",async () => {
                await this.change.save();
                return true;
            }).addAction("Нет", () => {
                this.change.reset();
                return true;
            }).addAction("Отмена", () => false)
            .lock();
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