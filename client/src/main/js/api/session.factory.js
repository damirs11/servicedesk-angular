"use strict";

SessionFactory.$inject = ["$injector"];
function SessionFactory($injector) {
    return $injector.instantiate(Session)
}

/**
 * @type {SD.User|null}
 */
let user = null;

/**
 * Реализует работу с сессией.
 * @class
 * @name Session
 */
class Session {

    static $inject = ["$connector", "SD"];

    constructor($connector, SD) {
        this.$connector = $connector;
        this.SD = SD;
    }

    /**
     * Входит в систему, возвращает user'а
     * @returns {Promise}
     */
    async login(login, password) {
        const data = {login, password};
        await this.$connector.post('rest/service/security/login', null, data);
        return this.authorize();
    }

    /**
     * Авторизоваться, получить текущего user'а
     */
    async authorize() {
        const data = await this.$connector.get('rest/service/config/getInfo');
        if (data.user) {
            return this.user = this.SD.User.parse(data.user);
        }
        return;
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