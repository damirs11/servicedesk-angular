import {Parse} from "./decorator/parse.decorator";
import {Instantiate, Nullable} from "./decorator/parse-utils";
import {Serialize} from "./decorator/serialize.decorator";
import {serializeId} from "./decorator/serialize-utils";

ApprovalProvider.$inject = ["EditableEntity", "SD"];
function ApprovalProvider(EditableEntity, SD) {
    /**
     * Голосование
     * @class
     * @extends SD.EditableEntity
     * @name SD.Approval
     */
    return class Approval extends EditableEntity {
        static $entityTypeId = 281478244794382;
        /**
         * ID сущности
         * @property
         * @name SD.Approval#approvalDescription
         * @type {String}
         */
        @Serialize("description")
        @Parse("description", Nullable( String )) subject;

        /**
         * Необходимое количество поддержавших
         * @property
         * @name SD.Approval#numberOfApproversRequired
         * @type {Number}
         */
        @Serialize() @Parse( Number ) numberOfApproversRequired;

        /**
         * Количество согласующих
         * @property
         * @name SD.Approval#numberOfApprovers
         * @type {Number}
         */
        @Serialize() @Parse( Number ) numberOfApprovers;

        /**
         * Количество одобривших
         * @property
         * @name SD.Approval#numberOfApproversApproved
         * @type {Number}
         */
        @Serialize() @Parse( Number ) numberOfApproversApproved;

        /**
         * Группа согласования
         * @property
         * @name SD.Approval#workgroup
         * @type {SD.Workgroup}
         */
        @Serialize("approvalWorkgroup", serializeId)
        @Parse("approvalWorkgroup", data => SD.Workgroup.parse(data) ) workgroup;

        /**
         * Статус
         * @property
         * @name SD.Approval#status
         * @type {SD.EntityStatus}
         */
        @Serialize(serializeId)
        @Parse(data => SD.EntityStatus.parse(data) ) status;

        /**
         * Крайний срок
         * @property
         * @name SD.Approval#deadline
         * @type {Date}
         */
        @Serialize() @Parse( Instantiate(Date) ) deadline;


        toString(){
            return this.name
        }

    };
}

export {ApprovalProvider};