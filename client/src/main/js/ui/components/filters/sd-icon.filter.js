const SDIconFilter = () => (entity) => {
    if (entity == null) return "sd-icon_unknown";
    switch (entity.constructor.name) {
        case "EntityPriority":
            return getEntityPriorityIcon(entity);
            break;
    }
};

const getEntityPriorityIcon = (priority) => {
    const order = priority.order;
    if (order == null) return "sd-icon_priority-unknown";
    return `sd-icon_priority-${order}`;
} ;

export {SDIconFilter}