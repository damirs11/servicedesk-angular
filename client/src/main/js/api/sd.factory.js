/**
 * Фабрика, предоставляющая SD
 */
import {EntityProvider} from "./entity/entity.provider";
import {UserProvider} from "./entity/user.provider";
import {PersonProvider} from "./entity/person.provider";
import {OrganizationProvider} from "./entity/organization.provider";
import {StatusProvider} from "./entity/entity-status.provider";
import {ChangeProvider} from "./entity/change.provider";
import {PriorityProvider} from "./entity/entity-priority.provider";
import {EditableEntityProvider} from "./entity/editable-entity.provider";
import {WorkgroupProvider} from "./entity/workgroup.provider";
import {HistoryLineProvider} from "./entity/history-line.provider";
import {RESTEntityProvider} from "./entity/rest-entity";
import {EntityCategoryProvider} from "./entity/entity-category.provider";
import {EntityClassificationProvider} from "./entity/entity-classification.provider";
import {EntityClosureCodeProvider} from "./entity/entity-closure-code.provider";
import {WorkorderProvider} from "./entity/workorder.provider";
import {HistoryableProvider} from "./entity/mixin/historyable.provider";
import {ApproverVoteProvider} from "./entity/approver-vote.provider";
import {ApprovableProvider} from "./entity/mixin/approvable.provider";
import {ApprovalProvider} from "./entity/approval.provider";
import {AttachmentProvider} from "./entity/attachment.provider";
import {FileInfoProvider} from "./entity/file-info.provider";
import {AttachmentsHolderProvider} from "./entity/mixin/attachments-holder.provider";
import {AccessibleProvider} from "./entity/mixin/accessible";
import {EntityAssignmentProvider} from "./entity/entity-assignment.provider";

SDFactory.$inject = ["$injector"];
function SDFactory($injector) {
    /**
     * Объект, предоставляющий классы SD
     * @type {SD}
     */
    const SD = $injector.instantiate(SDConstructor,{ cache: Object.create(null) });

    if ('GULP_REPLACE:DEBUG') window._SD = SD;

    return Object.freeze(SD);
}

/**
 * Переменная объявленна, чтобы JSDoc не показывал ошибку на ENTITY_MIXIN.*
 * Все микисины хранятся в SD и доступны только при создании классов для SD.
 */
const ENTITY_MIXIN = null;
/**
 * Класс, предоставляющий доступ к классам сущностей ServiceDesk
 * @constructor
 * @name SD
 */
const SDConstructor = function SD($injector,cache) {
    /** Классы для сущностей */
    /** Базовые классы */
    const Entity = $injector.instantiate(EntityProvider,{cache});
    const RESTEntity = $injector.instantiate(RESTEntityProvider,{Entity,SD:this});
    const EditableEntity = $injector.instantiate(EditableEntityProvider,{Entity,RESTEntity,SD:this});
    /** Пробосится в зависимости для классов сущностей */
    const locals = {SD:this, Entity, RESTEntity, EditableEntity};

    /** Миксины */
    locals.Historyable = $injector.instantiate(HistoryableProvider,locals);
    locals.Approvable = $injector.instantiate(ApprovableProvider,locals);
    locals.AttachmentsHolder = $injector.instantiate(AttachmentsHolderProvider,locals);
    locals.Accessible = $injector.instantiate(AccessibleProvider,locals);

    /** Все остальные сущности */
    this.User = $injector.instantiate(UserProvider,locals);
    this.Person = $injector.instantiate(PersonProvider,locals);
    this.Organization = $injector.instantiate(OrganizationProvider,locals);
    this.Change = $injector.instantiate(ChangeProvider,locals);
    this.Workorder = $injector.instantiate(WorkorderProvider,locals);
    this.Workgroup = $injector.instantiate(WorkgroupProvider,locals);
    this.HistoryLine = $injector.instantiate(HistoryLineProvider,locals);
    this.ApproverVote = $injector.instantiate(ApproverVoteProvider,locals);
    this.Approval = $injector.instantiate(ApprovalProvider,locals);
    this.Attachment = $injector.instantiate(AttachmentProvider,locals);
    this.FileInfo = $injector.instantiate(FileInfoProvider,locals);

    /** другие-сущности */
    this.EntityStatus = $injector.instantiate(StatusProvider,locals);
    this.EntityAssignment = $injector.instantiate(EntityAssignmentProvider,locals);
    this.EntityPriority = $injector.instantiate(PriorityProvider,locals);
    this.EntityCategory = $injector.instantiate(EntityCategoryProvider,locals);
    this.EntityClassification = $injector.instantiate(EntityClassificationProvider,locals);
    this.EntityClosureCode = $injector.instantiate(EntityClosureCodeProvider,locals);

    // this.withCache = (newCache = Object.create(cache)) => {
    //     return $injector.instantiate(SD,{cache:newCache});
    // };

    Object.defineProperty(this,"withCache",{
        enumerable: false,
        value: (newCache = Object.create(cache)) => {
            return $injector.instantiate(SD,{cache:newCache});
        }
    });

    Object.defineProperty(this,"clearCache",{
        enumerable: false,
        value: () => {
            Object.keys(this.cache).forEach(key => delete this.cache[key]);
        }
    });

    return Object.freeze(this)
};
SDConstructor.$inject = ["$injector","cache"];


export {SDFactory};