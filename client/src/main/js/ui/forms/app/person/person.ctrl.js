class PersonController{

    $inject = ["SD","$stateParams"];

    constructor(SD,$stateParams){
        this.SD = SD;

        this.personId = $stateParams.personId;
        this.loadPerson()
    }

    async loadPerson(){
        this.person = {
            "oid":281491084750137,
            "name":"TEST USER SD",
            "firstName":"TEST",
            "lastName":"SD",
            "middleName":"USER",
            "job": "Специалист",
            "organization": {
                name: "КАРО Фильм Москва 1",
                oid: 281494575710572
            }
        } // MocPerson
    }

}

export {PersonController as controller}