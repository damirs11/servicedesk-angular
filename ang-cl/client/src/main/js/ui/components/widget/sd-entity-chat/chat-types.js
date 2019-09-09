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

const PROBLEM_MESSAGE_TYPES = {
    PROBLEM_EXECUTOR: new MessageType("PROBLEM_MANAGER", "Комментарий исполнитель", "commentToExecutor"),
    PROBLEM_INITIATOR: new MessageType("PROBLEM_INITIATOR", "Комментарий инициатору", "commentToInitiator"),
};

const WORKORDER_MESSAGE_TYPES = {
    WORKORDER_INITIATOR: new MessageType("WORKORDER_INITIATOR", "Комментарий по инициатору", "commentToInitiator"),
    WORKORDER_EXECUTOR: new MessageType("WORKORDER_EXECUTOR", "Комментарий исполнителю", "commentToExecutor")
};

const SERVICECALL_MESSAGE_TYPES = {
    SERVICECALL_INITIATOR: new MessageType("SERVICECALL_INITIATOR", "Комментарий инициатору", "commentToInitiator"),
    SERVICECALL_EXECUTOR: new MessageType("SERVICECALL_EXECUTOR", "Комментарий исполнителю", "commentToExecutor")
};

export {CHANGE_MESSAGE_TYPES, WORKORDER_MESSAGE_TYPES, SERVICECALL_MESSAGE_TYPES};