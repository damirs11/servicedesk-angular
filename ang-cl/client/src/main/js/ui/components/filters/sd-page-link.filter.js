const SDPageLinkFilter = ($state) => (entity) => {
    if (entity == null) return null;
    let stateName = null;
    let params = null;
    switch (entity.constructor.name) {
        case "Change":
            stateName = "app.change.card.view";
            params = {changeId: entity.id};
            break;
        default: return null;
    }
    return $state.href(stateName, params)
};

SDPageLinkFilter.$inject = ["$state"];

export {SDPageLinkFilter}