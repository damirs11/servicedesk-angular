import errorMessages from "./messages.html";

AddMessages.$inject = ["$templateCache"];
export function AddMessages($templateCache){
    $templateCache.put("template.ngMessage.errorMessages",errorMessages)
}