// Шаблон стандартных сообщений для валидаторов полей ввода
import errorMessages from "./messages.html";
// Приоритет
import priorityCell from "./cells/priority.cell.html"
// Персона
import personCell from "./cells/person.cell.html"
// Просрочено? Да-Нет
import expiredCell from "./cells/expired.cell.html"

Templates.$inject = ["$templateCache"];
function Templates($templateCache){
    $templateCache.put("template.ngMessage.errorMessages", errorMessages);
    $templateCache.put("template.grid.cell.priority", priorityCell);
    $templateCache.put("template.grid.cell.person", personCell);
    $templateCache.put("template.grid.cell.expired", expiredCell);
}

export {Templates};