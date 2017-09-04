/**
 * Фабрика, предоставляющая SD
 */
import {EntityProvider} from "./entity/entity.provider";
import {UserProvider} from "./entity/user.provider";
import {PersonProvider} from "./entity/person.provider";
import {OrganizationProvider} from "./entity/organization.provider";
import {StatusProvider} from "./entity/entity-status.provider";
import {ChangeProvider} from "./entity/change.provider";
import {PriorityProvider} from "./entity/entity-priority.provider";
import {EditableEntityProvider} from "./entity/editable-entity.provider";
import {WorkgroupProvider} from "./entity/workgroup.provider";
import {HistoryLineProvider} from "./entity/history-line.provider";
import {RESTEntityProvider} from "./entity/rest-entity";
import {EntityCategoryProvider} from "./entity/entity-category.provider";
import {EntityClassificationProvider} from "./entity/entity-classification.provider";

SDFactory.$inject = ["$injector"];
function SDFactory($injector) {
    /**
     * Объект, предоставляющий классы SD
     * @type {SD}
     */
    const SD = $injector.instantiate(SDConstructor,{ cache: Object.create(null) });

    if ('GULP_REPLACE:DEBUG') window._SD = SD;

    return Object.freeze(SD);
}

/**
 * Класс, предоставляющий доступ к классам сущностей ServiceDesk
 * @constructor
 * @name SD
 */
const SDConstructor = function SD($injector,cache) {
    /** Классы для сущностей */
    /** Базовые классы */
    const Entity = $injector.instantiate(EntityProvider,{cache});
    const RESTEntity = $injector.instantiate(RESTEntityProvider,{Entity,SD:this});
    const EditableEntity = $injector.instantiate(EditableEntityProvider,{Entity,RESTEntity,SD:this});
    /** Пробосится в зависимости для классов сущностей */
    const locals = {SD:this, Entity, RESTEntity, EditableEntity};

    /** Все остальные сущности */
    this.User = $injector.instantiate(UserProvider,locals);
    this.Person = $injector.instantiate(PersonProvider,locals);
    this.Organization = $injector.instantiate(OrganizationProvider,locals);
    this.Change = $injector.instantiate(ChangeProvider,locals);
    this.Workgroup = $injector.instantiate(WorkgroupProvider,locals);
    this.HistoryLine = $injector.instantiate(HistoryLineProvider,locals);

    this.EntityStatus = $injector.instantiate(StatusProvider,locals);
    this.EntityPriority = $injector.instantiate(PriorityProvider,locals);
    this.EntityCategory = $injector.instantiate(EntityCategoryProvider,locals);
    this.EntityClassification = $injector.instantiate(EntityClassificationProvider,locals);

    this.withCache = (newCache = Object.create(cache)) => {
        return $injector.instantiate(SD,{cache:newCache});
    };

    this.clearCache = () => {
        Object.keys(this.cache)
            .forEach(key => delete this.cache[key])
        ;
    };

    return Object.freeze(this)
};
SDConstructor.$inject = ["$injector","cache"];


export {SDFactory};