UserResolver.$inject = ["Session"];
/**
 * Авторизуется на сервере с помощью кукисов.
 * @param Session {Session}
 */
export function UserResolver(Session){
    return Session.authorize();
}