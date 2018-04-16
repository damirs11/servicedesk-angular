// Новое изменение внедряется зависимостью.
// Все для того, чтобы передавать без проблем его между дочерними стейтами.
function NewChangeResolver(SD) {
    const change = new SD.Change();
    change.$update({
        assignment: new SD.EntityAssignment(),
    });
    return change;
}
NewChangeResolver.$inject = ["SD"];

export {NewChangeResolver};