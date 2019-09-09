class UIDisplayedError extends Error {
    name = "UIDisplayedError";
    body;
    sourceError;

    constructor(message,body,sourceError){
        super(message);
        this.body = body;
        this.sourceError = sourceError;
    }
}

function handleUIDisplayedError(error, ModalAction, $rootScope) {
    ModalAction.uiDisplayedError($rootScope,{error});
}
handleUIDisplayedError.$inject = ["error", "ModalAction", "$rootScope"];

if ('GULP_REPLACE:DEBUG') window._UIDisplayedError = UIDisplayedError;

export {UIDisplayedError, handleUIDisplayedError}