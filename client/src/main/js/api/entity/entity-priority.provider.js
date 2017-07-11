PriorityProvider.$inject = ["Entity"];
function PriorityProvider(Entity) {
    /**
     * Приоритет
     */
    return class Priority extends Entity {

        constructor(id){
            super(id);
            if (id !== undefined) this.id = id
        }

        ["parse:name"](value) {
            return value;
        }

        toString(){
            return this.name
        }
    };
}

export {PriorityProvider};