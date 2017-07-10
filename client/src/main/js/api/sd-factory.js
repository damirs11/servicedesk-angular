import {EntityProvider} from "./entity/entity-provider";
import {UserProvider} from "./entity/user-provider";
import {PersonProvider} from "./entity/person-provider";
import {OrganizationProvider} from "./entity/organization-provider";
import {ChangeProvider} from "./entity/change-provider";

/**
 * Фабрика, предоставляющая SD
 */
SDFactory.$inject = ["$injector", "$connector"];
function SDFactory($injector, $connector) {
    /**
     * Текущий пользователь.
     * @type {User}
     */
    let user = null;

    /**
     * Объект, содержащий основные методы для работы с бэкэндом и классы для сущностей.
     */
    const SD = {
        /**
         * Войти в систему
         */
        async login(login, password){
            const data = {login, password};
            await $connector.post('rest/service/security/login', null, data);
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
            let params = {oldPassword, newPassword};
            return $connector.post("rest/service/security/passwordChange", params);
        },
        /**
         * Выйти из системы
         */
        async logout(){
            await $connector.get('rest/service/security/logout');
            SD.user = null;
        },
        get user() {
            return user;
        },
        set user(value) {
            user = value;
        },
        get authorized() {
            return Boolean(user)
        }
    };

    /**
     * Классы для сущностей
     */
    SD.Entity = $injector.instantiate(EntityProvider);
    SD.User = $injector.instantiate(UserProvider, {SD, Entity: SD.Entity});
    SD.Person = $injector.instantiate(PersonProvider, {SD, Entity: SD.Entity});
    SD.Organization = $injector.instantiate(OrganizationProvider, {SD, Entity: SD.Entity});
    SD.Change = $injector.instantiate(ChangeProvider, {SD, Entity: SD.Entity});

    return Object.freeze(SD);
}

export {SDFactory};