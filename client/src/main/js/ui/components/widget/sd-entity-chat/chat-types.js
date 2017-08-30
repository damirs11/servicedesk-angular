/**
 * @class
 * @name MessageType
 * @desc описание типа сообщения
 */
class MessageType {
    constructor(side,title,role){
        this.side = side;
        this.title = title;
        this.role = role;
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
    INITIATOR: new MessageType(0, "Комментарий исполнителю", "Инитиатор"),
    DEADLINE_CHANGE: new MessageType(1, "<b>Изменение крайнего срока</b>")
};



export {CHANGE_MESSAGE_TYPES}