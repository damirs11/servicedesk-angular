/**
 * @type {SD.User|null}
 */
import {NGInject, NGInjectClass} from "../common/decorator/ng-inject.decorator";
"use strict";

SessionFactory.$inject = ["$injector"];
function SessionFactory($injector) {
    return $injector.instantiate(Session)
}

let user = null;

/**
 * Права доступа к типам сущностей.
 * @type Object<string,SDTypeAccessRules>
 */
let typeAccessRules = {};

/**
 * Реализует работу с сессией.
 * @class
 * @name Session
 */
@NGInjectClass()
class Session {

    @NGInject() $connector;
    @NGInject() SD;
    @NGInject() $sdAccess;

    /**
     * Входит в систему, возвращает user'а
     * @returns {Promise}
     */
    async login(login, password) {
        const loginData = {login, password};
        const data = await this.$connector.post('rest/service/security/login', null, loginData);
        this.user = this.SD.User.parse(data);
        await this._loadAccessData();
        return this.user;
    }

    async _loadAccessData() {
        const entityTypeList = ["Change", 'Workorder', 'Person', 'Organization'];
        const accessDataArray = await Promise.all(
            entityTypeList.map(type => this.$sdAccess.loadTypeAccessRules(type))
        );
        for (let i = 0; i < entityTypeList.length; i++) {
            const type = entityTypeList[i];
            typeAccessRules[type] = accessDataArray[i]
        }
    }

    /**
     * Авторизоваться, получить текущего user'а
     */
    async authorize() {
        const data = await this.$connector.get('rest/service/config/getInfo');
        if (data.user) { // Успешная авторизация по кукисам
            user = this.SD.User.parse(data.user);
            await this._loadAccessData();
        }
        return user;
    }

    /**
     * Смена пароля
     */
    async changePassword(oldPassword, newPassword) {
        if (!this.authorized) return;
        const params = {oldPassword, newPassword};
        return this.$connector.post("rest/service/security/passwordChange", null, params);
    }

    /**
     * Выйти из системы
     */
    async logout() {
        await this.$connector.get('rest/service/security/logout');
        this.user = null;
        typeAccessRules = {};
    }

    getTypeAccessRules(type){
        return typeAccessRules[type]
    }

    get user() {
        return user;
    }

    set user(value) {
        user = value;
    }

    get authorized() {
        return Boolean(user)
    }
}

export {SessionFactory};