import {EntityProvider} from "./Entity/EntityProvider"
import {UserProvider} from "./User/UserProvider"


SDFactory.$inject = ["$injector","$http"];
export function SDFactory($injector, $http) {
    let user = null;

    const SD = {
        async login(login,password){
            const params = {login,password};
            const loginResponse = await $http.post('rest/service/security/login', params);
            return this.authorize();
        },
        async authorize(){
            const response = await $http.get('rest/service/config/getInfo');
            if (!response.data.user.isAuthorized) {
                user = null;
                return;
            }
            SD.user = SD.User.parse(response.data.user);
            return SD.user;
        },
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

    SD.user = null; // Текущий пользователь

    SD.Entity = $injector.instantiate(EntityProvider);
    SD.User = $injector.instantiate(UserProvider,{SD,Entity:SD.Entity});

    return Object.freeze(SD)
}