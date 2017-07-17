import {EntityProvider} from "./entity/entity.provider";
import {UserProvider} from "./entity/user.provider";
import {PersonProvider} from "./entity/person.provider";
import {OrganizationProvider} from "./entity/organization.provider";
import {StatusProvider} from "./entity/entity-status.provider";
import {PriorityProvider} from "./entity/entity-priority.provider";
import {EditableEntityProvider} from "./entity/editable-entity.provider";

/**
 * Фабрика, предоставляющая SD
 */
SDFactory.$inject = ["$injector"];
function SDFactory($injector) {
    /**
     * Текущий пользователь.
     * @type {User}
     */
    let user = null;

    /**
     * Глобальный кэш для объектов.
     */
    const globalCache = Object.create(null);

    /**
     * Объект, предоставляющий классы SD
     * @type {SD}
     */
    const sd = new SD($injector,globalCache);

    /**
     * @debug
     * Для дебага кладем SD в глобальную переменную
     */
    window.SD = sd;

    return Object.freeze(sd);
}

/**
 * Класс, предоставляющий доступ к классам сущностей ServiceDesk
 * @class
 * @name SD
 */
class SD {

    constructor($injector, cache){
        this.$injector = $injector;
        this.cache = cache;
        /** Классы для сущностей */
        /** Базовый класс */
        this.Entity = $injector.instantiate(EntityProvider,{cache});
        this.EditableEntity = $injector.instantiate(EditableEntityProvider,{Entity:this.Entity,SD:this});
        /** Пробосится в зависимости для классов сущностей */
        const locals = {SD:this, Entity: this.Entity, EditableEntity:this.EditableEntity};

        /** Все остальные сущности */
        this.User = $injector.instantiate(UserProvider,locals);
        this.Person = $injector.instantiate(PersonProvider,locals);
        this.Organization = $injector.instantiate(OrganizationProvider,locals);
        this.Status = $injector.instantiate(StatusProvider,locals);
        this.Priority = $injector.instantiate(PriorityProvider,locals);
    }

    /**
     * Возвращает SD с новым cache
     * Если передать newCache - кэш будет изолирован от прошлых.
     * Иначе создатся объект, который наследуется от прошлого кэша.
     */
    withCache(cache){
        let newCache;
        if (cache != null && typeof cache === "object") {
            newCache = cache
        } else {
            newCache = Object.create(this.cache);
        }
        return Object.freeze(new SD(this.$injector,newCache))
    }

    /**
     * Очищает текущий кэш.
     * Не затрагивает дочерний и родительский кэши.
     */
    clearCache(){
        const keys = Object.keys(this.cache);
        for (let i = 0; i < keys.length; i++) {
            const key = keys[i];
            delete this.cache[key]
        }
    }
}

export {SDFactory};