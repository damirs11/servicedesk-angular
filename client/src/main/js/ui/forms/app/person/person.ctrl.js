class PersonController{

    $inject = ["SD","$stateParams"];

    constructor(SD,$stateParams){
        this.personId = $stateParams.personId
    }

}

export {PersonController as controller}