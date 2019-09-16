import {NGInject, NGInjectClass} from "../../common/decorator/ng-inject.decorator";
PageLockFactory.$inject = ["$injector"];
function PageLockFactory($injector) {
    return ($scope) => $injector.instantiate(PageLock,{$scope});
}

@NGInjectClass()
class PageLock {
    @NGInject() $scope;
    @NGInject() $state;
    @NGInject() $transitions;
    @NGInject() ModalAction;

    /**
     * Текст, который будет отображен в модальном окне
     * @type {string}
     */
    $text = "Вы действительно хотите покинуть страницу?";
    /**
     * Текст, который будет отображен в модальном окне
     * @type {string}
     */
    $title = "Внимание";
    /**
     * Флаг, указывающий, что действия еще не изменяли
     * @type {boolean}
     */
    $defaultActions = true;
    /**
     * Действия.
     * @type {{name: string, action: Function}[]} $actions
     */
    $actionInfos = [
        {name:"да",action:() => true},
        {name:"нет",action:() => false}
    ];
    /**
     * Дейтсвие при отмене. Клик по крестику или вне модального окна
     * @type {Function}
     * @returns {PageLock}
     */
    $cancelAction = () => false;
    // Действия, при котором нас оповестят, что мы покидаем страницу
    $condition = undefined;

    /**
     * Задает текст.
     * @param value {string}
     * @returns {PageLock}
     */
    setText(value) {
        this.$text = value;
        return this;
    }
    /**
     * Задает заголовок.
     * @param value {string}
     * @returns {PageLock}
     */
    setTitle(value) {
        this.$title = value;
        return this;
    }
    /**
     * Задает условие. Если функция вовзращает true - срабатывает блокировка страницы.
     * @param value {Function}
     * @returns {PageLock}
     */
    setCondition(value) {
        this.$condition = value;
        return this;
    }
    /**
     * Добавляет действия.
     * Действия должны возвращать boolean.
     * true - покинуть страницу.
     * false - остаться.
     * @param name {string} - название действия. Будет на кнопке
     * @param value {Function} - функция. Выполнится при нажатии на кнопку
     * @param btnClass {string} - классы кнопки у модального окна (например "btn btn-danger")
     *
     * @returns {PageLock}
     */
    addAction(name,value,btnClass) {
        if (this.$defaultActions) {
            this.$actionInfos = [];
            this.$defaultActions = false;
        }
        const info = {name,action:value};
        if (btnClass) info.btn = btnClass;
        this.$actionInfos.push(info);
        return this;
    }

    /**
     * Задает действие при отмене, простом закрытии модального окна.
     * @param value
     * @returns {PageLock}
     */
    setCancelAction(value) {
        this.$cancelAction = value;
        return this;
    }
    /**
     * Убирает все действия
     * @returns {PageLock}
     */
    clearActions(){
        this.$actions = [];
        return this;
    }

    $locked = false;
    $onBeforeUnload() {
        if (!this.$condition()) return;
        console.log("onUnload");
        event.returnValue = this.$text;
        return event.returnValue;
    }
    $removeExitHook = null;

    /**
     * Блокирует страницу от "случайного" выхода
     * @returns {PageLock}
     */
    lock(){
        if (this.$locked) return this;
        const fromCurrentState = {
            from: state => state.name === this.$state.current.name,
        };
        this.$removeExitHook = this.$transitions.onExit(fromCurrentState,async () => {
            if (!this.$condition()) return;
            const modalResult = await this.ModalAction.leavePage( // ToDo создать модальное окно
                this.$scope,
                {text: this.$text, title: this.$title, actionInfos: this.$actionInfos}
            );
            if (modalResult == null) {
                return this.$cancelAction();
            }
            const info = this.$actionInfos[modalResult];
            return info.action();
        });
        window.addEventListener("beforeunload", this.$onBeforeUnload);
        this.$scope.$on("$destroy", ::this.unlock);

        this.$locked = true;
        return this;
    }

    /**
     * Снимает блокировку
     * @returns {PageLock}
     */
    unlock(){
        if (!this.$locked) return this;
        this.$removeExitHook();
        window.removeEventListener("beforeunload", this.$onBeforeUnload);
        this.$locked = false;
        return this
    }
}

export {PageLockFactory}