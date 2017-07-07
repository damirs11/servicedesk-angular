import {AbstractGrid} from "../../../components/widget/grid/abstract-grid";

class TestGridOptions extends AbstractGrid {

    constructor($scope) {
        super($scope);
        this.columnDefs = [
            {name: 'id'},
            {name: 'name'},
            {name: 'title'},
            {name: 'age', displayName: 'Age (not focusable)', allowCellFocus: false}
        ];
        this.data = [
            {id: 1, name: 'Маша', age: 30},
            {id: 2, name: 'Паша', title: 'Кукушка'}
        ];
        this.totalItems = 2;
    }
}

export {TestGridOptions};