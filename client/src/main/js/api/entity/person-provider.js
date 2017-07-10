PersonProvider.$inject = ["Entity", "SD","$connector"];
function PersonProvider(Entity, SD, $connector) {
    /**
     * Персона
     */
    return class Person extends Entity {


        ["parse:sex"](value) {
            return value;
        }

        ["parse:email"](value) {
            return value;
        }

        ["parse:job"](value) {
            return value;
        }

        ["parse:firstName"](value) {
            return value;
        }

        ["parse:lastName"](value) {
            return value;
        }

        ["parse:middleName"](value) {
            return value;
        }

        ["parse:organization"](value) {
            return value && SD.Organization.parse(value);
        }

        static find(filter){

        }

        static getById(id){
            $connector.get()
        }
    };
}

export {PersonProvider};