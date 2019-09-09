// Новое изменение внедряется зависимостью.
// Все для того, чтобы передавать без проблем его между дочерними стейтами.
function NewProblemResolver(SD) {
    const problem = new SD.Problem();
    problem.$update({
        assignment: new SD.EntityAssignment(),
    });
    return problem;
}
NewProblemResolver.$inject = ["SD"];

export {NewProblemResolver};