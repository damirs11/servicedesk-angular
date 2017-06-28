function QDecorator($delegate){
    window.Promise = $delegate;
    return $delegate;
}
QDecorator.$inject = ["$delegate"];

export {QDecorator};