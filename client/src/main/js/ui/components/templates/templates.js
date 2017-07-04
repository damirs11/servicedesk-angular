// Шаблон стандартных сообщений для валидаторов полей ввода
import errorMessages from "./messages.html";

Templates.$inject = ["$templateCache"];
function Templates($templateCache){
    $templateCache.put("template.ngMessage.errorMessages", errorMessages);
}

export {Templates};