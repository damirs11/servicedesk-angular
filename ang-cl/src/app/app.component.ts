import { Component } from '@angular/core';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent {
  constructor() {}

  get user() {
    return true;
    //return this.Session.user;
  }

  get authorized(){
    return true;
    // return this.Session.authorized;
  }

  async logout() {
      // await this.Session.logout();
      // this.$state.go("app.main");
  }

  loginClick(){
      // this.$state.go("app.login");
  }

  /**
   * Право на просмотр "Изменений"
   * @returns {Boolean}
   */
  get canSeeTabChanges(): boolean {
    return true;
    //return this.Session.getTypeAccessRules("Change") && this.Session.getTypeAccessRules("Change").isReadEntityAllowed;
  }
  /**
   * Право на просмотр "Заявок"
   * @returns {Boolean}
   */
  get canSeeTabServiceCalls(): boolean {
    return true;
    //return this.Session.getTypeAccessRules("ServiceCall") && this.Session.getTypeAccessRules("ServiceCall").isReadEntityAllowed;
  }
  /**
   * Право на просмотр "Нарядов"
   * @returns {Boolean}
   */
  get canSeeTabWorkorders(): boolean {
    return true;
    //return this.Session.getTypeAccessRules("Workorder") && this.Session.getTypeAccessRules("Workorder").isReadEntityAllowed;
  }
  /**
   * Право на просмотр "Персон"
   * @returns {Boolean}
   */
  get canSeeTabPersons(): boolean {
    return true;
    // return this.Session.getTypeAccessRules("Person").isReadEntityAllowed
  }
  /**
   * Право на просмотр "Объектов" (обслуживания)
   * @returns {Boolean}
   */
  get canSeeTabConfigurationItems(): boolean {
    return true;
    // return this.Session.getTypeAccessRules("ConfigurationItem").isReadEntityAllowed
  }
  /**
   * Право на просмотр "Изменений"
   * @returns {Boolean}
   */
  get canSeeTabProblems(): boolean {
      return true; // Не реализован доступ к правам
  }
}
