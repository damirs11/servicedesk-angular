import errorMessages from "./messages.html";



AddMessages.$inject = ["$templateCache"];
/**
 * Функция заносит html с сообщениями об ошибках в кэш
 * Выполняется через module.run()
 * ToDo переделать в отдельный компонент ангуляра
 * @param $templateCache
 * @constructor
 */
export function AddMessages($templateCache){
    $templateCache.put("template.ngMessage.errorMessages",errorMessages)
}