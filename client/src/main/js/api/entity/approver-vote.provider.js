import {Parse} from "./decorator/parse.decorator";
import {Nullable} from "./decorator/parse-utils";
import {Serialize} from "./decorator/serialize.decorator";
import {serializeId} from "./decorator/serialize-utils";

ApproverVoteProvider.$inject = ["EditableEntity", "SD"];
function ApproverVoteProvider(EditableEntity, SD) {
    /**
     * Голосование
     * @class
     * @extends SD.RESTEntity
     * @name SD.ApproverVote
     */
    return class ApproverVote extends EditableEntity {
        /**
         * ID сущности, к которой привязаны голосо
         * @property
         * @name SD.ApproverVote#entityId
         * @type {Number}
         */
        @Serialize()
        @Parse( Number ) entityId;

        /**
         * Согласовано
         * @property
         * @name SD.ApproverVote#approved
         * @type {Number}
         */
        @Serialize()
        @Parse( Number ) approved;

        /**
         * Согласующий
         * @property
         * @name SD.ApproverVote#approver
         * @type {SD.Person}
         */
        @Serialize(serializeId)
        @Parse( data => SD.Person.parse(data) ) approver;

        /**
         * Причина
         * @property
         * @name SD.ApproverVote#reason
         * @type {String}
         */
        @Serialize("reason")
        @Parse("reason", Nullable(String) ) reason;

        /**
         * Тип сущности
         * @property
         * @name SD.ApproverVote#reason
         * @type {String}
         */
        @Serialize()
        @Parse( Nullable(String) ) entityType;
        toString(){
            return this.name
        }

    };
}

export {ApproverVoteProvider};