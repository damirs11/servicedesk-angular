OrganizationProvider.$inject = ["Entity"];
function OrganizationProvider(Entity) {
    /**
     * Организация
     */
    return class Organization extends Entity {

        ["parse:name"](value) {
            return value;
        }

        ["parse:email"](value) {
            return value;
        }

    };
}

export {OrganizationProvider};