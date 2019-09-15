import {
  NGInject,
  NGInjectClass
} from "../../common/decorator/ng-inject.decorator";
import { SDAccessRules } from "./sd-access-rules";
import { SDTypeAccessRules } from "./sd-type-access-rules";

@NGInjectClass()
class SDAccess {
  /**
   * @type {Object.<string, Object.<string,[function]>>}
   */
  @NGInject() globalFieldRules;
  /**
   * @type {Object.<string, Object.<string,[function]>>}
   */
  @NGInject() globalActionRules;
  /**
   * @type {Object.<string, Object.<string,string>>}
   */
  @NGInject() renamedFields;
  @NGInject() $connector;
  @NGInject() $injector;

  /**
   * добавляет правило доступа к полю
   * Функция должна возвращать одно из значений  SDACCESS_FIELD_*
   * При проверке проверяются все правила.
   * Ответом будет самый низкий уровень доступа из всех полученных.
   * @see SDACCESS_FIELD_HIDE
   * @see SDACCESS_FIELD_READ
   * @see SDACCESS_FIELD_WRITE
   */
  addFieldAccessRule(entityType, fieldName, rule) {
    let entityRules = this.globalFieldRules[entityType];
    if (!entityRules) entityRules = this.globalFieldRules[entityType] = {};
    let fieldRules = entityRules[fieldName];
    if (!fieldRules) fieldRules = entityRules[fieldName] = [];
    fieldRules.push(rule);
  }

  /**
   * добавляет правило доступа к полю
   * Функция должна возвращать одно из значений SDACCESS_ACTION_*
   * При проверке проверяются все правила.
   * Ответом будет самый низкий уровень доступа из всех полученных.
   * @see SDACCESS_ACTION_DENY
   * @see SDACCESS_ACTION_ALLOW
   */
  addActionAccessRule(entityType, actionName, rule) {
    let entityRules = this.globalActionRules[entityType];
    if (!entityRules) entityRules = this.globalActionRules[entityType] = {};
    let actionRules = entityRules[actionName];
    if (!actionRules) actionRules = entityRules[actionName] = [];
    actionRules.push(rule);
  }

  /**
   * Переименовывает поле.
   * При проверке прав в объекте с сервера, будет проверяться
   * не по названию поля, а по заданному здесь для поля имени
   */
  setFieldName(entityType, fieldName, newName) {
    let entityRenames = this.renamedFields[entityType];
    if (!entityRenames) entityRenames = this.renamedFields[entityType] = {};
    entityRenames[fieldName] = newName;
  }

  /**
   * Подгружает права на конкретную сущность
   * @param entityOrType {SD.Entity|string} - тип сущности строкой или сама сущность
   * @param id {number} - необходим, если entity передан строкой
   * @return {Promise.<SDAccessRules>}
   */
  async loadAccessRules(entityOrType, id) {
    let entityType, entityId;
    if (typeof entityOrType === "string") {
      entityType = entityOrType;
      entityId = id;
    } else {
      entityType = entityOrType.constructor.$entityType;
      entityId = entityOrType.id;
    }
    //todo добавить вариант запроса прав доступа для новой сущности (без id)
    const serverAccessData = await this.$connector.get(
      `rest/service/security/access/${entityType}/${entityId}`
    );
    const fieldRules = this.globalFieldRules[entityType] || {};
    const actionRules = this.globalActionRules[entityType] || {};
    const renamedFields = this.renamedFields[entityType] || {};
    return this.$injector.instantiate(SDAccessRules, {
      serverAccessData,
      fieldRules,
      actionRules,
      renamedFields
    });
  }

  /**
   * Права доступа к типу сущности в общем, не основываясь на конкретной сущности.
   * @param entityType {string} - тип сущности
   * @return {Promise.<SDTypeAccessRules>}
   */
  async loadTypeAccessRules(entityType) {
    const serverAccessData = await this.$connector.get(
      `rest/service/security/access/${entityType}`
    );
    const actionRules = this.globalActionRules[entityType] || {};
    return this.$injector.instantiate(SDTypeAccessRules, {
      serverAccessData,
      actionRules
    });
  }
}

// Ключи-значения доступа к полям
const SDACCESS_FIELD_HIDE = 0;
const SDACCESS_FIELD_READ = 1;
const SDACCESS_FIELD_WRITE = 2;
// Ключи-значения доступа к действиям
const SDACCESS_ACTION_DENY = false;
const SDACCESS_ACTION_ALLOW = true;

export {
  SDAccess,
  SDACCESS_FIELD_HIDE,
  SDACCESS_FIELD_READ,
  SDACCESS_FIELD_WRITE,
  SDACCESS_ACTION_DENY,
  SDACCESS_ACTION_ALLOW
};
