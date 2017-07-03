PriorityProvider.$inject = ["Entity"];
function PriorityProvider(Entity) {
    /**
     * Приоритет
     */
    return class Priority extends Entity {
        
        ["parse:name"](value) {
            return value;
        }

    };
}

export {PriorityProvider};