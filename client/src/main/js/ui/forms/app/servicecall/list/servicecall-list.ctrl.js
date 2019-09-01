import {UIEntityFilter} from "../../../../utils/ui-entity-filter";
import {EntityTypes} from "../../../../../api/entity/util/entity-types";
import {BaseEntityList} from "../../entity-pages/base-list";
import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ServiceCallListController extends BaseEntityList {

    @NGInject() SD;
    @NGInject() $scope;
    @NGInject() $grid;
    @NGInject() $state;
    @NGInject() Session;
    @NGInject() $location;
    /** Критерии поиска, находящиеся в ссылке */
    @NGInject("searchParams") urlSearchParams = {};

    async $onInit () {
        this.grid = new this.$grid.ServiceCallGrid(this.$scope,this.SD);
        this.name = "ServiceCall";
        this.stateCreated = "app.servicecall.create.common";
        this.stateView = "app.servicecall.card.view";
        this.entityId = EntityTypes.ServiceCall;
        super.$onInit();
    }

    async configFilter() {
        const filters = this.filters = [];
        filters.push(this.defaultSearchFilter);
        filters.push(new UIEntityFilter("Назначенные мне","executor"));
        filters.push(new UIEntityFilter("Созданные мной","creator"));
        filters.push(new UIEntityFilter({divider: true}));
        filters.push(new UIEntityFilter("Новые","new"));
        filters.push(new UIEntityFilter({divider: true}));

        const personId = this.Session.user.person.id;
        const groups = await this.SD.Workgroup.list({personId:personId});
        if (groups && groups.length) {
            const uiGroups = groups.map(g => new UIEntityFilter(g.name,`group_${g.id}`));
            const groupFilter = new UIEntityFilter({name: "Назначенные группе", childs: uiGroups});
            filters.push(groupFilter);
        }
    }
}

export {ServiceCallListController as controller};