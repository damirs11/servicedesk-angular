/**
 * Идентификаторы типов сущностей.
 */
import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";

const TYPE_MAP = {
    "Change": 724041768,
    "Workorder": 556859410,
    "Servicecall": 563019801,
};

EntityTypeProvider.$inject = ["Entity"];
function EntityTypeProvider(Entity) {
    /**
     * Статус
     * @class
     * @name SD.EntityType
     * @extends SD.Entity
     */
    return class EntityType extends Entity {
        /**
         * @static
         * @method
         * @name SD.EntityType#getFor
         * @param entity - сущность
         * @returns {SD.EntityType}
         */
        static getFor(entity) {
            let type = entity.constructor.$entityType;
            if (type == null) type = entity.constructor.name;
            if (! TYPE_MAP[type]) return null;
            const data = {
                id: TYPE_MAP[type]
            };
            return this.parse(data)
        }

        toString(){
            return this.name
        }
    };
}

export {EntityTypeProvider};