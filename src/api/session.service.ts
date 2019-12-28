import { User } from './entity/user/user';
import { UserService } from './entity/user/user.service';
import { first } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { EntityModule } from './entity.module';

/**
 * Права доступа к типам сущностей.
 * @type Object<string,SDTypeAccessRules>
 */
let typeAccessRules: object = {};

let user: User;

/**
 * Реализует работу с сессией.
 * @class
 * @name Session
 */
@Injectable({
  providedIn: EntityModule
})
export class Session {
  // @NGInject() $http;
  // @NGInject() SD;
  // @NGInject() $sdAccess;

  constructor(private UserService: UserService) { }

  /**
   * Входит в систему, возвращает user'а
   * @returns {User | null}
   */
  public login(login: string, password: string): User | null {
    var loginData: object = { login, password };
    this.UserService.post<User>("rest/service/security/login", null, loginData)
      .subscribe((rUser: User) => this.user = rUser);
    this._loadAccessData();
    return this.user;
  }

  public _loadAccessData(): void {
    const entityTypeList = ["Change", "Workorder", "Person", "Organization", "ServiceCall"];
    //TODO убрать заглушку с правами const accessDataArray = Promise.all(entityTypeList.map(type => this.$sdAccess.loadTypeAccessRules(type)));
    const accessDataArray = entityTypeList;
    for (let i = 0; i < entityTypeList.length; i++) {
      const type = entityTypeList[i];
      typeAccessRules[type] = accessDataArray[i];
    }
  }

  /**
   * Авторизоваться, получить текущего user'а
   */
  public authorize() {
    this.UserService.get("rest/service/config/getInfo")
      .subscribe((rUser: User) => this.user = rUser);
    if (this.user) {
      // Успешная авторизация по кукисам
      this._loadAccessData();
    }
    return user;
  }

  /**
   * Смена пароля
   */
  public changePassword(oldPassword: string, newPassword: string) {
    if (!this.authorized) return;
    const params = {
      oldPassword,
      newPassword
    };
    return this.UserService.post("rest/service/security/passwordChange", null, params);
  }

  /**
   * Выйти из системы
   */
  public logout() {
    this.UserService.get("rest/service/security/logout");
    this.user = null;
    typeAccessRules = {};
  }

  public getTypeAccessRules(type) {
    return typeAccessRules[type];
  }

  public get user() {
    return this.user;
  }

  public set user(value) {
    this.user = value;
  }

  public get authorized() {
    return Boolean(this.user);
  }
}
