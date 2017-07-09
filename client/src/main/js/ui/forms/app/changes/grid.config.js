import {AbstractGrid} from "../../../components/widget/grid/abstract-grid";

class ChangesGridOptions extends AbstractGrid {

    constructor($scope) {
        super($scope);
        this.columnDefs = [
            { field: 'status', name: "Статус" },
            { field: 'description', name: "Тема" },
            { field: 'name', name: "Тема" },
            { field: 'date', name: "Дата", enableColumnResizing: false, cellFilter: "dd.MM.yyyy"},
            { field: 'prefix', name: "Обращение", cellTooltip: true },
            { field: 'company', name: "Компания", enableColumnMenu: false }
        ];
        this.data = [
        ];
        //this.totalItems = 2;
    }
}

export {ChangesGridOptions};