import { SDAccess } from "./sd-access";

function SDAccessProvider() {
  /**
   * Правила доступа к полям, которые добавлены
   * с помощью config файлов
   * {entityType:{field: [function], field2: [function],...},...}
   * @type {Object.<string, Object.<string,[function]>>}
   */
  const globalFieldRules = {};
  /**
   * Правила доступа к действиям, которые добавлены
   * с помощью config файлов
   * {entityType:{action: [function], action2: [function],...},...}
   * @type {Object.<string, Object.<string,[function]>>}
   */
  const globalActionRules = {};
  /**
   * Переименованные поля.
   * {entityType:{field: newName, action2: newName2,...},...}
   * @type {Object.<string, Object.<string,string>>}
   */
  const renamedFields = {};

  /**
   * Методы дублируют SDAccess
   * @see SDAccess
   **/
  const provider = {
    addFieldAccessRule(entityType, fieldName, rule) {
      let entityRules = globalFieldRules[entityType];
      if (!entityRules) entityRules = globalFieldRules[entityType] = {};
      let fieldRules = entityRules[fieldName];
      if (!fieldRules) fieldRules = entityRules[fieldName] = [];
      fieldRules.push(rule);
    },
    addActionAccessRule(entityType, actionName, rule) {
      let entityRules = globalActionRules[entityType];
      if (!entityRules) entityRules = globalActionRules[entityType] = {};
      let actionRules = entityRules[actionName];
      if (!actionRules) actionRules = entityRules[actionName] = [];
      actionRules.push(rule);
    },
    setFieldName(entityType, fieldName, newName) {
      let entityRenames = renamedFields[entityType];
      if (!entityRenames) entityRenames = renamedFields[entityType] = {};
      entityRenames[fieldName] = newName;
    },
    $get($injector) {
      const $sdAccess = $injector.instantiate(SDAccess, {
        globalFieldRules,
        globalActionRules,
        renamedFields
      });
      if ("GULP_REPLACE:DEBUG") window._$sdAccess = $sdAccess;
      return $sdAccess;
    }
  };
  provider.$get.$inject = ["$injector"];
  return provider;
}

export { SDAccessProvider };
