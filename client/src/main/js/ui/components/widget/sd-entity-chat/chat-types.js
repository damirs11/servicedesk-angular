/**
 * @class
 * @name MessageType
 * @desc описание типа сообщения
 */
class MessageType {
    constructor(side,title,role){
        this.side = side; // Сторона. 0-2 <=> лево/центр/право
        this.title = title; // Заголовок сообщения
        this.role = role; // Роль. Отображается под иконкой персоны
    }

    get isLeft() {
        return this.side == 0
    }

    get isMiddle() {
        return this.side == 1
    }

    get isRight() {
        return this.side == 2
    }
}

const CHANGE_MESSAGE_TYPES = {
    MANAGER: new MessageType(2, "Комментарий инициатору", "Менеджер"),
    INITIATOR: new MessageType(0, "Комментарий исполнителю", "Инициатор"),
    DEADLINE_CHANGE: new MessageType(1, "<b>Изменение крайнего срока</b>")
};

const WORKORDER_MESSAGE_TYPES = {
    DOER: new MessageType(2, "Комментарий инициатору", "Исполнитель"),
    INITIATOR: new MessageType(0, "Комментарий исполнителю", "Инициатор"),
    DEADLINE_CHANGE: new MessageType(1, "<b>Изменение крайнего срока</b>"),
    DEADLINE_CHANGE_REASON: new MessageType(2, "<b>Причина переноса крайнего срока</b>","Исполнитель"),
};



export {CHANGE_MESSAGE_TYPES, WORKORDER_MESSAGE_TYPES}