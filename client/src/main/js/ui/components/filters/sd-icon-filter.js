const SDIconFilter = () => (entity) => {
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