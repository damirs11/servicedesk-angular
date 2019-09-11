import {Parse} from './decorator/parse.decorator';
import {Nullable} from './decorator/parse-utils';
import {Serialize} from './decorator/serialize.decorator';
import {serializeId} from './decorator/serialize-utils';


/**
 * Голосование
 * @class
 * @extends SD.RESTEntity
 * @name SD.ApproverVote
 */
export class ApproverVote {
    /**
     * ID сущности, к которой привязаны голосо
     * @property
     * @name SD.ApproverVote#entityId
     * @type {Number}
     */
    @Serialize()
    entityId: number;

    /**
     * Согласовано
     * @property
     * @name SD.ApproverVote#approved
     * @type {Number}
     */
    @Serialize()
    approved: number;

    /**
     * Согласующий
     * @property
     * @name SD.ApproverVote#approver
     * @type {SD.Person}
     */
    @Serialize(serializeId)
    approver;

    /**
     * Причина
     * @property
     * @name SD.ApproverVote#reason
     * @type {String}
     */
    @Serialize('reason')
    reason: string;

    /**
     * Тип сущности
     * @property
     * @name SD.ApproverVote#reason
     * @type {String}
     */
    @Serialize()
    entityType: string;
}
