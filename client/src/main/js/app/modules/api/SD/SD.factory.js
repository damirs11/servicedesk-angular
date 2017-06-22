import {EntityProvider} from "./Entity/EntityProvider"
import {UserProvider} from "./User/UserProvider"


SDFactory.$inject = ["$injector","$http"];
/**
 * Фабрика, предоставляющая SD
 */
export function SDFactory($injector, $http) {
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
            const params = {login,password};
            console.log(params);
            const loginResponse = await $http.post('rest/service/security/login', params);
            console.log(loginResponse);
            return this.authorize();
        },
        /**
         * Авторизоваться, получить текущего user'а
         */
        async authorize(){
            const response = await $http.get('rest/service/config/getInfo');
            if (!response.data.user.isAuthorized) {
                user = null;
                return;
            }
            SD.user = SD.User.parse(response.data.user);
            return SD.user;
        },
        /**
         * Выйти из системы
         */
        async logout(){
            await $http.get('rest/service/security/logout');
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