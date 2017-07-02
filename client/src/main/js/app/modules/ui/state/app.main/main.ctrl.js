class MainController {
    static $inject = ['SD',"$scope","ModalAction"];

    constructor(SD,$scope,ModalAction){
        this.SD = SD;
        this.$scope = $scope;
        this.ModalAction = ModalAction;
    }

    async testGetMessage(){
        try {
            const message = await this.ModalAction.getMessage(this.$scope,{
                header: "Проверка сообщений",
                placeholder: "Введите тестовое сообщение (5-10 символов)",
                labelOk: "ОК",
                minLength: 5,
                maxLength: 10
            });
            console.log(`Ввели ${message}`)
        } catch (result) {
            console.log("Ввод текста отменен.", result)
        }
    }

    async testAlert(){
        await this.ModalAction.alert(this.$scope,{
            header: "Заголовок тестового сообщения",
            style: "dialog-header-notify",
            msg: "Возникло плановое нажатие на кнопку проверки диалогового сообщения. Пожалуйста, закройте это окно, чтобы проверить роботоспособность до конца.",
        });
    }

    async testConfirm(){
        try {
            const result = await this.ModalAction.confirm(this.$scope, {
                header: "Произошло нажатие на кнопку",
                msg: "Подтвердите нажатие на кнопку."
            });
            if (result) console.log(`Пользователь нажал на кнопку`);
            else console.log(`Пользователь не нажимал на кнопку`)
        } catch (e) {
            console.log("Пользователь решил не отвечать")
        }
    }
}

export {MainController}