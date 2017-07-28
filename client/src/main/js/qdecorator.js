QDecorator.$inject = ["$delegate"];
function QDecorator($q){
    window.Promise = $q;
    return $q;
}

export {QDecorator};