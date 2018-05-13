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
    MANAGER: new MessageType("MANAGER","Комментарий менеджеру", "commentToManager"),
    INITIATOR: new MessageType("INITIATOR", "Комментарий инициатору", "commentToInitiator"),
    EXECUTOR: new MessageType("EXECUTOR", "Комментарий исполнителю", "commentToExecutor")
};

const WORKORDER_MESSAGE_TYPES = {
    DOER: new MessageType("Комментарий инициатору"),
    INITIATOR: new MessageType("Комментарий исполнителю"),
    DEADLINE_CHANGE: new MessageType("<b>Изменение крайнего срока</b>"),
    DEADLINE_CHANGE_REASON: new MessageType("<b>Причина переноса крайнего срока</b>"),
};



export {CHANGE_MESSAGE_TYPES, WORKORDER_MESSAGE_TYPES}