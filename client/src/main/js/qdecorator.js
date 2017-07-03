QDecorator.$inject = ["$delegate"];
function QDecorator($delegate){
    window.Promise = $delegate;
    return $delegate;
}

export {QDecorator};