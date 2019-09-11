import { Serialize } from './decorator/serialize.decorator';
import { serializeId } from './decorator/serialize-utils';
import { Parse } from './decorator/parse.decorator';

export class EntityAssignment {
    /**
     * Исполнитель
     * @property
     * @name SD.EntityAssignment#executor
     * @type {SD.Person}
     */
    @Serialize(serializeId)
    executor;

    /**
     * Приоритет
     * @property
     * @name SD.EntityAssignment#priority
     * @type {SD.EntityPriority}
     */
    @Serialize(serializeId)
    priority;

    /**
     * Статус
     * @property
     * @name SD.EntityAssignment#status
     * @type {SD.EntityStatus}
     */
    @Serialize(serializeId)
    status;

    /**
     * Рабочая группа
     * @property
     * @name SD.EntityAssignment#workgroup
     * @type {SD.Workgroup}
     */
    @Serialize(serializeId)
    workgroup;
}
