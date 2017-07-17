import {AbstractGrid} from "../../../components/widget/grid/abstract-grid";

class QuestionsGridOptions extends AbstractGrid {

    constructor($scope) {
        super($scope);
        this.columnDefs = [
            { field: 'no', name: "Номер" },
            { field: 'status', name: "Статус" },
            { field: 'createdDate', name: "Дата создания", enableColumnResizing: false, cellFilter: "dd.MM.yyyy hh:mm"},
            { field: 'deadline', name: "Крайний срок", enableColumnResizing: false, cellFilter: "dd.MM.yyyy hh:mm"},
            { field: 'resolveDate', name: "Фактически выполнено", enableColumnResizing: false, cellFilter: "dd.MM.yyyy hh:mm"},
            { field: 'priority', name: "Приоритет" },
            { field: 'subject', name: "Тема" },
            { field: 'initiator', name: "Инициатор" },
            { field: 'manager', name: "Менеджер" }
        ];
        this.data = [
        ];
        //this.totalItems = 2;
    }
}

export {QuestionsGridOptions};