UserProvider.$inject = ["SD","Entity"];
export function UserProvider(SD,Entity){
    return class User extends Entity{

        ["parse:name"](value){ return value; }
        ["parse:login"](value){ return value; }
        ["parse:roles"](value){ return value; }
        ["parse:person"](value){ return value; }

    }
}