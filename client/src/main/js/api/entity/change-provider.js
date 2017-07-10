ChangeProvider.$inject = ["Entity", "SD"];
function ChangeProvider(Entity, SD) {
    /**
     * Изменение
     */
    return class Change extends Entity {

        ["parse:no"](value) {
            return value;
        }

        ["parse:status"](value) {
            return value;
        }

        ["parse:createdDate"](value) {
            return value;
        }

        ["parse:deadline"](value) {
            return value;
        }

        ["parse:resolveDate"](value) {
            return value;
        }

        ["parse:priority"](value) {
            return value;
        }

        ["parse:subject"](value) {
            return value;
        }

        ["parse:initiator"](value) {
            return SD.Person.parse(value);
        }

        ["parse:manager"](value) {
            return SD.Person.parse(value);
        }

    };
}

export {ChangeProvider};