import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ChangeCreateController{
    // NG зависимости
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() $state;
    @NGInject() $pageLock;

    headerTabs = [
        {name:'Общее',sref:'app.change.create.common'},
        {name:'Вложения',sref:'app.change.create.attachments'},
    ];

    async $onInit() {
        this.change = new this.SD.Change();
        // this.registerLeaveEditListener();
    }


    registerLeaveEditListener() {
        this.$pageLock(this.$scope)
            .setTitle("Несохраненные изменения")
            .setText("Внимание! Изменение не было сохранено, сохранить их перед уходом?")
            .setCondition(() => this.change.checkModified())
            .addAction("Да",async () => {
                await this.change.create();
                return true;
            }).addAction("Нет", () => {
                return true;
            }).addAction("Отмена", () => false)
            .lock();
    }


}

export {ChangeCreateController as controller}