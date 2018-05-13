/**
 * @class
 * @name MessageType
 * @desc описание типа сообщения
 */
class MessageType {
    constructor(name, title, fieldName){
        this.name = name; // имя типа для определения на сервере, совпадает с HistoryType.name
        this.title = title; // Заголовок сообщения
        this.fieldName = fieldName; //название поля сущности для определения прав доступа
    }

}

const CHANGE_MESSAGE_TYPES = {
    CHANGE_MANAGER: new MessageType("CHANGE_MANAGER", "Комментарий менеджеру", "commentToManager"),
    CHANGE_INITIATOR: new MessageType("CHANGE_INITIATOR", "Комментарий инициатору", "commentToInitiator"),
    CHANGE_EXECUTOR: new MessageType("CHANGE_EXECUTOR", "Комментарий исполнителю", "commentToExecutor")
};

const WORKORDER_MESSAGE_TYPES = {
    WORKORDER_EXECUTOR: new MessageType("WORKORDER_EXECUTOR", "Комментарий исполнителю", "commentToExecutor"),
    WORKORDER_INITIATOR: new MessageType("WORKORDER_INITIATOR", "Комментарий по выполнению", "solution")
};



export {CHANGE_MESSAGE_TYPES, WORKORDER_MESSAGE_TYPES}