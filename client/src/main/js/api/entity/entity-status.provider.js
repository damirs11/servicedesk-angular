StatusProvider.$inject = ["Entity"];
function StatusProvider(Entity) {
    /**
     * Статус
     */
    return class Status extends Entity {

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

export {StatusProvider};