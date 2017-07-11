class PersonController{

    /**
     * Занят ли контроллер. Будет отображат анимацию загрузки
     * @type {boolean}
     */
    busy = false;

    static $inject = ["SD","personId"];
    constructor(SD,personId){
        this.SD =  SD;
        this.personId = personId;
        this.$onInit()
    }

    $onInit(){
        this.person = null;
        this.error = null;
        this._loadPerson()
    }

    async _loadPerson(){
        try {
            this.busy = true; // Контроллер
            this.person = await new this.SD.Person(this.personId).load();
        } catch (e) {
            this.error = e || true;
        } finally {
            this.busy = false;
        }
    }

}

export {PersonController as controller}