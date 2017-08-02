class SDTextareaController{

    get isEnabled() {
        if (this.enabled === undefined) return true;
        return this.enabled;
    }

    get value(){
        if (this.target == null) return this.emptyValue || "- нет -";
        return this.target
    }
}

export {SDTextareaController as controller}