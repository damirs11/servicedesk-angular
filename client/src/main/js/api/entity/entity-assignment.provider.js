import {Parse} from "./decorator/parse.decorator";
import {Serialize} from "./decorator/serialize.decorator";
import {serializeId} from "./decorator/serialize-utils";

EntityAssignmentProvider.$inject = ["Entity","SD"];
function EntityAssignmentProvider(Entity,SD) {
    /**
     * "Назначено"
     * @class
     * @name SD.EntityAssignment
     */
    return class EntityAssignment extends Entity {
        /**
         * Исполнитель
         * @property
         * @name SD.EntityAssignment#executor
         * @type {SD.Person}
         */
        @Serialize(serializeId)
        @Parse(data => SD.Person.parse(data)) executor;

        /**
         * Приоритет
         * @property
         * @name SD.EntityAssignment#priority
         * @type {SD.EntityPriority}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityPriority.parse(data)) priority;

        /**
         * Статус
         * @property
         * @name SD.EntityAssignment#status
         * @type {SD.EntityStatus}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityStatus.parse(data)) status;

        /**
         * Рабочая группа
         * @property
         * @name SD.EntityAssignment#workgroup
         * @type {SD.Workgroup}
         */
        @Serialize(serializeId)
        @Parse(data => SD.Workgroup.parse(data)) workgroup;
    };
}

export {EntityAssignmentProvider};