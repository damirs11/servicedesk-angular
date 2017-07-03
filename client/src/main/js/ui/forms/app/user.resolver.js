UserResolver.$inject = ["SD"];
export function UserResolver(SD){
    return SD.authorize();
}