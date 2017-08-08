// Шаблон стандартных сообщений для валидаторов полей ввода
import errorMessages from "./messages.html";
import priorityCell from "./cells/priority.cell.html"
import personCell from "./cells/person.cell.html"

Templates.$inject = ["$templateCache"];
function Templates($templateCache){
    $templateCache.put("template.ngMessage.errorMessages", errorMessages);
    $templateCache.put("template.grid.cell.priority", priorityCell);
    $templateCache.put("template.grid.cell.person", personCell);

}

export {Templates};