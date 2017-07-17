import {EntityProvider} from "./entity/entity.provider";
import {UserProvider} from "./entity/user.provider";
import {PersonProvider} from "./entity/person.provider";
import {OrganizationProvider} from "./entity/organization.provider";
import {StatusProvider} from "./entity/entity-status.provider";
import {PriorityProvider} from "./entity/entity-priority.provider";
import {ChangeProvider} from "./entity/change.provider";
import {EditableEntityProvider} from "./entity/editable-entity.provider";

/**
 * Фабрика, предоставляющая SD
 */
SDFactory.$inject = ["$injector"];
function SDFactory($injector) {
    /**
     * Объект, предоставляющий классы SD
     * @type {SD}
     */
    const SD = $injector.instantiate(SDConstructor,{ cache: Object.create(null) });

    /**
     * @debug
     * Для дебага кладем SD в глобальную переменную
     */
    window._SD = SD;

    return Object.freeze(SD);
}

/**
 * Класс, предоставляющий доступ к классам сущностей ServiceDesk
 * @constructor
 * @name SDConstructor
 */
const SDConstructor = function SD($injector,cache) {
    /** Классы для сущностей */
    /** Базовый класс */
    const Entity = $injector.instantiate(EntityProvider,{cache});
    const EditableEntity = $injector.instantiate(EditableEntityProvider,{Entity,SD:this});
    /** Пробосится в зависимости для классов сущностей */
    const locals = {SD:this, Entity, EditableEntity};

    /** Все остальные сущности */
    this.User = $injector.instantiate(UserProvider,locals);
    this.Person = $injector.instantiate(PersonProvider,locals);
    this.Organization = $injector.instantiate(OrganizationProvider,locals);
    this.Status = $injector.instantiate(StatusProvider,locals);
    this.Priority = $injector.instantiate(PriorityProvider,locals);
    this.Change = $injector.instantiate(ChangeProvider,locals);

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