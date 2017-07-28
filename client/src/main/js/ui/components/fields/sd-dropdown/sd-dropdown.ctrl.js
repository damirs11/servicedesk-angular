const MAX_DISPLAY_VALUES = 20;

class SDDropdownComponentController{

    get isEnabled() {
        if (this.enabled === undefined) return true;
        return this.enabled;
    }

    get debounceTime() {
        return this.debounce || 1000;
    }

    display(value){
        if (value == null) return this.emptyValue;
        return this.displayValue({$value:value}) || value.toString();
    }

    values = null;
    lastFetchRequest = null;
    async fetchValues(text){
        if (this.cache && this.values) return;
        if (this.lastFetchRequest == text) return;
        this.values = null;
        try {
            const array = await this.fetchData({$text:text});
            array.splice(MAX_DISPLAY_VALUES,array.length);
            this.values = array;
            this.lastFetchRequest = text
        } catch (e) {
            throw e;
        }
    }
    //
    // get searchCriteria(){
    //     if (!this.searchBy) return "$select.search";
    //     let criteria = "{ ";
    //     this.searchBy.split(/, |,/)
    //         .map(field => `${field}:$select.search`)
    //         .join(",")
    //         .forEach(field => criteria += field)
    //     ;
    //     criteria += "}";
    //     return criteria
    // }

    get isAllowClear(){
        if (this.allowEmpty === undefined) return false;
        return this.allowEmpty;
    }
}

export {SDDropdownComponentController as controller};