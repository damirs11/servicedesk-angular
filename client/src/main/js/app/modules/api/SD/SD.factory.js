import {EntityProvider} from "./Entity/EntityProvider"
import {UserProvider} from "./User/UserProvider"


SDFactory.$inject = ["$injector","$connector"];
/**
 * Фабрика, предоставляющая SD
 */
export function SDFactory($injector, $connector) {
    /**
     * Текущий юзер.
     * @type {User}
     */
    let user = null;

    /**
     * Объект, содердащий основные методы для работы с бэкэндом и классы для сущностей.
     */
    const SD = {
        /**
         * Войти в систему
         */
        async login(login,password){
            const data = {login,password};
            await $connector.post('rest/service/security/login', data);
            return this.authorize();
        },
        /**
         * Авторизоваться, получить текущего user'а
         */
        async authorize(){
            const data = await $connector.get('rest/service/config/getInfo');
            if (!data.user.isAuthorized) {
                user = null;
                return;
            }
            SD.user = SD.User.parse(data.user);
            return SD.user;
        },
        /**
         * Смена пароля
         */
        async changePassword(oldPassword, newPassword){
            if (!SD.authorized) return;
            var params = {oldPassword, newPassword};
            return $connector.post("rest/service/security/passwordChange",params);
        },
        /**
         * Выйти из системы
         */
        async logout(){
            await $connector.get('rest/service/security/logout');
            SD.user = null;
        },
        get user(){
            return user;
        },
        set user(value){
            user = value;
        },
        get authorized(){
            return Boolean(user)
        }
    };

    /**
     * Классы для сущностей
     */
    SD.Entity = $injector.instantiate(EntityProvider);
    SD.User = $injector.instantiate(UserProvider,{SD,Entity:SD.Entity});

    return Object.freeze(SD)
}