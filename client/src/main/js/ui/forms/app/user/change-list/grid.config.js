const columns = [
    {
        field: "status",
        displayName: "Статус",
        width: 90
    },
    {
        field: "subject",
        displayName: "Тема",
        width: 90
    }
];

const GridConfig = {
    columnDefs: columns, // Хидеры столбцов.
    enableCellEdit: false, // Запрещаем редактирование
    enableColumnResizing: false, // Запрещаем изменение размера
    enableRowSelection: true,
    enableSorting: true,
    headerRowHeight: 30,
    rowHeight: 30,
};

export {GridConfig};