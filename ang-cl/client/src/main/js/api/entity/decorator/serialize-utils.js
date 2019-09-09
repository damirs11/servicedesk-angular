function serializeId(entity) {
    if (entity == null) return null;
    return {id:entity.id};
}

export {serializeId}