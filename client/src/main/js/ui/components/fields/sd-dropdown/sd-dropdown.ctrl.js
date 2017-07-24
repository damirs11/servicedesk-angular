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


    get isAllowClear(){
        if (this.allowClear === undefined) return false;
        return this.allowClear;
    }
}

export {SDDropdownComponentController as controller};