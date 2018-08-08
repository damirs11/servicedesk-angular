import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";
const ERROR_HISTORY_READ_DISALLOWED = Symbol("ERROR_HISTORY_READ_DISALLOWED");

/**
 * Страница истории для сущности.
 * Обязательные resolve параметры:
 * @param getEntity {function} - функция, которая венрнет сущность.
 */

@NGInjectClass()
class EntityCardHistoryController{
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() getEntity;
    @NGInject() $grid;

    /**
     * Промис загрузки данных
     */
    fetchPromise;


    async $onInit() {
        this.entity = this.getEntity();
        this.accessRules = this.entity.accessRules;
        if (!this.accessRules.isReadHistoryAllowed) return;
        const grid = this.grid = new this.$grid.HistoryGrid(this.$scope,this.SD,this.entity);
        this.$scope.$on("grid:fetch",::this.$onTableFetch);
        grid.fetchData();
    }


    $onTableFetch(ignored,event) {
        if (this.fetchPromise != null) return;
        this.fetchPromise = event.fetchPromise
    }
}

export {EntityCardHistoryController as controller}