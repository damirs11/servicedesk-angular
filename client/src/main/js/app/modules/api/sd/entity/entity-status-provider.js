StatusProvider.$inject = ["Entity"];
function StatusProvider(Entity) {
    /**
     * Статус
     */
    return class Status extends Entity {

        ["parse:name"](value) {
            return value;
        }

    };
}

export {StatusProvider};