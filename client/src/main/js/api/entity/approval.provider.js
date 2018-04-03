import {Parse} from "./decorator/parse.decorator";
import {Instantiate, Nullable} from "./decorator/parse-utils";
import {Serialize} from "./decorator/serialize.decorator";
import {serializeId} from "./decorator/serialize-utils";
import {TYPEID_APPROVAL} from "./entity-type-list";

ApprovalProvider.$inject = ["EditableEntity", "SD"];
function ApprovalProvider(EditableEntity, SD) {
    /**
     * Голосование
     * @class
     * @extends SD.EditableEntity
     * @name SD.Approval
     * Имеет миксин, который внедряет в сущности методы для согласований
     * @see ENTITY_MIXIN.Approvable
     */
    return class Approval extends EditableEntity {
        static $entityTypeId = TYPEID_APPROVAL;
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

        /**
         * Идентификатор сущности,
         * которой принадлежит согласование
         * @property
         * @name SD.Approval#ownerEntityType
         * @type {Number}
         * @ATTENTION
         * Т.к. это поле должно отправляться при сохранении всегда,
         * метод сериализации не используются. Оно добавляется
         * в переопределенном методе save
         * @link save
         * @link SD.Approval#save
         */
        @Parse("entityType", (data) => data.id) ownerEntityType;

        /**
         * @override
         * Переопределение, чтобы добавлять всегда поле entityType
         */
        async save(data){
            const saveData = data || this.$modifiedData;
            saveData["entityType"] = {id: this.ownerEntityType};
            return super.save(saveData);
        }


        toString(){
            return this.name
        }

    };
}

export {ApprovalProvider};