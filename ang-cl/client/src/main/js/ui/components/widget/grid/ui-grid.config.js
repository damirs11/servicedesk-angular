UiGridConfig.$inject = ["$animateProvider"];
function UiGridConfig($animateProvider) {
    // Отключаем анимацию в заголовках столбцов таблицы
    $animateProvider.classNameFilter(/^((?!(ui-grid-menu)).)*$/);
}
export {UiGridConfig}