import errorMessages from "./messages.html";

AddMessages.$inject = ["$templateCache"];
function AddMessages($templateCache){
    $templateCache.put("template.ngMessage.errorMessages", errorMessages)
}

export {AddMessages};