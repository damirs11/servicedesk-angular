import {Parse} from './decorator/parse.decorator';
import {Instantiate, Nullable} from './decorator/parse-utils';
import {Serialize} from './decorator/serialize.decorator';
import {serializeId} from './decorator/serialize-utils';
import {EntityTypes} from './util/entity-types';
import {Mixin} from './mixin/mixin.decorator';

/**
 * Голосование
 * @class
 * @extends SD.EditableEntity
 * @name SD.Approval
 * Имеет миксин, который внедряет в сущности методы для согласований
 * @see ENTITY_MIXIN.Approvable
 */
@Mixin(Accessible)
export class Approval {
    static $entityTypeId = EntityTypes.Approval;
    /**
     * ID сущности
     * @property
     * @name SD.Approval#approvalDescription
     * @type {String}
     */
    @Serialize('description')
    subject: string;

    /**
     * Необходимое количество поддержавших
     * @property
     * @name SD.Approval#numberOfApproversRequired
     * @type {Number}
     */
    @Serialize() numberOfApproversRequired: number;

    /**
     * Количество согласующих
     * @property
     * @name SD.Approval#numberOfApprovers
     * @type {Number}
     */
    @Serialize() numberOfApprovers: number;

    /**
     * Количество одобривших
     * @property
     * @name SD.Approval#numberOfApproversApproved
     * @type {Number}
     */
    @Serialize() numberOfApproversApproved: number;

    /**
     * Группа согласования
     * @property
     * @name SD.Approval#workgroup
     * @type {SD.Workgroup}
     */
    @Serialize('approvalWorkgroup', serializeId)
    workgroup;

    /**
     * Статус
     * @property
     * @name SD.Approval#status
     * @type {SD.EntityStatus}
     */
    @Serialize(serializeId)
    status;

    /**
     * Крайний срок
     * @property
     * @name SD.Approval#deadline
     * @type {Date}
     */
    @Serialize() deadline: Date;

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
    ownerEntityType: number;


    /**
     * Инициатор согласования
     * @property
     * @name SD.Approval#initiator
     * @type {SD.Person}
     */
    @Serialize(serializeId)
    initiator;

    /**
     * @override
     * Переопределение, чтобы добавлять всегда поле entityType
     */
    // Метод из SD.EditableEntity
    // async save(data) {
    //     const saveData = data || this.$modifiedData;
    //     saveData['entityType'] = {id: this.ownerEntityType};
    //     return super.save(saveData);
    // }
}
