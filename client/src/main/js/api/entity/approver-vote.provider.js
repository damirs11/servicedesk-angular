import {Parse} from "./decorator/parse.decorator";
import {Nullable} from "./decorator/parse-utils";

ApproverVoteProvider.$inject = ["RESTEntity", "SD"];
function ApproverVoteProvider(RESTEntity, SD) {
    /**
     * Голосование
     * @class
     * @extends SD.RESTEntity
     * @name SD.ApproverVote
     */
    return class ApproverVote extends RESTEntity {
        /**
         * ID сущности, к которой привязаны голосо
         * @property
         * @name SD.ApproverVote#entityId
         * @type {Number}
         */
        @Parse( Number ) entityId;

        /**
         * Согласовано
         * @property
         * @name SD.ApproverVote#approved
         * @type {Number}
         */
        @Parse( Number ) approved;

        /**
         * Согласующий
         * @property
         * @name SD.ApproverVote#approver
         * @type {SD.Person}
         */
        @Parse( data => SD.Person.parse(data) ) approver;

        /**
         * Причина
         * @property
         * @name SD.ApproverVote#reason
         * @type {String}
         */
        @Parse( Nullable(String) ) reason;

        /**
         * Тип сущности
         * @property
         * @name SD.ApproverVote#reason
         * @type {String}
         */
        @Parse( Nullable(String) ) entityType;

        toString(){
            return this.name
        }

    };
}

export {ApproverVoteProvider};