import {Parse} from "./decorator/parse.decorator";


PersonProvider.$inject = ["Entity", "SD","$connector"];
function PersonProvider(Entity, SD, $connector) {
    /**
     * Персона
     * @class
     * @name SD.Person
     */
    return class Person extends Entity {
        /**
         * Пол персоны: 0 - женский, 1 - мужской
         * @property
         * @name SD.Person#sex
         * @type {string}
         */
        @Parse(Number) sex;
        /**
         * Тут будут доки всех полей.
         */
        @Parse(String) email;
        @Parse(String) job;
        @Parse(String) firstName;
        @Parse(String) lastName;
        @Parse(String) middleName;
        @Parse(data => SD.Organization.parse(data)) organization;

        constructor(id){
            super(id);
            if (id !== undefined) this.id = id
        }

        async load(){
            const data = await $connector.get(`rest/entity/Person/${this.id}`);
            debugger;
            return this.$update(data);
        }
    };
}

export {PersonProvider};