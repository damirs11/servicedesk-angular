OrganizationProvider.$inject = ["Entity"];
function OrganizationProvider(Entity) {
    /**
     * Организация
     */
    return class Organization extends Entity {

        constructor(id){
            super(id);
            if (id !== undefined) this.id = id
        }

        ["parse:name"](value) {
            return value;
        }

        ["parse:email"](value) {
            return value;
        }


        toString(){
            return this.name
        }
    };
}

export {OrganizationProvider};