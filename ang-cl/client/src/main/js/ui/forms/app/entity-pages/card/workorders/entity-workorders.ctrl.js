import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

/**
 * Страница истории для сущности.
 * Обязательные resolve параметры:
 * @param getEntity {function} - функция, которая венрнет сущность.
 * Обязательные data параметры
 * @param entityProps {Object} - объект, содержащий названия полей
 * Необязательные
 * @param entityProps.common {String} - заменяет все 3 параметра ниже. приставки Id и прочие присываются тут.
 * @param entityProps.fetchParam {String} - название параметра при поиске наряда ("change", "servicecall", etc.).
 * @param entityProps.workorderField {String} - название поля у наряда, где хранится наша сущность (Изменения в "workorder.change" -> "change")
 * @param entityProps.workorderCreateParam {String} - Название параметра при создании наряда (обычно changeId, servicecallId и т.п.)
 *
 */


@NGInjectClass()
class EntityCardWorkordersController{
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() getEntity;
    @NGInject() $state;
    @NGInject() $grid;
    @NGInject() ModalAction;
    /**
     * Промис загрузки данных
     */
    fetchPromise;

    $onInit(){
        this.entity = this.getEntity();
        this.entityProps = this.$state.current.data.entityProps;
        this.initProps();
        this.grid = new this.$grid.WorkorderGrid(this.$scope,
            this.SD,
            "grid.workorders.attachedTo-"+this.entityProps.workorderField
        );
        const searchParams = {};
        searchParams[this.entityProps.fetchParam] = this.entity.id;
        this.gridSearchParams.add(searchParams);
        this.$scope.$on("grid:fetch",this.$onTableFetch);
        this.grid.fetchData();
    }

    initProps(){
        if (this.entityProps.common) {
            this.entityProps.workorderField = this.entityProps.fetchParam = this.entityProps.common;
            this.entityProps.workorderCreateParam = this.entityProps.common + "Id";
        }
    }

    $onTableFetch(ignored,event) {
        if (this.fetchPromise != null) return;
        this.fetchPromise = event.fetchPromise
    }

    get gridSearchParams(){
        return this.grid.searchParamsContainer
    }

    async clickAttach(){
        await this.ModalAction.attachWorkorder(this.$scope, {entity: this.entity, workorderField: this.entityProps.workorderField});
        this.grid.fetchData();
    }

    clickOpen(){
        const rows = this.grid.getSelectedRows();
        if (rows.length != 1) return;
        const row = rows[0];
        this.$state.go("app.workorder.card.view", {workorderId: row.id});
    }

    get isButtonOpenDisabled() {
        return this.grid.getSelectedRows().length != 1;
    }

    clickCreateWorkorder(){
        const stateParams = {};
        stateParams[this.entityProps.workorderCreateParam] = this.entity.id;
        this.$state.go("app.workorder.create.common", stateParams);
    }
}

export {EntityCardWorkordersController as controller}