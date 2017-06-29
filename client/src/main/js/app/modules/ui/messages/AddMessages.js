import errorMessages from "./messages.html";

AddMessages.$inject = ["$templateCache"];
export function AddMessages($templateCache){
    $templateCache.put("errorMessages",errorMessages)
}