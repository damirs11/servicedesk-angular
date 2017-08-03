const MAX_DISPLAY_VALUES = 20;

class SDDropdownComponentController{

    static $inject = ["$attrs"];

    constructor($attrs){
        this.$attrs = $attrs;
    }

    get isEnabled() {
        if (this.enabled === undefined) return true;
        return this.enabled;
    }

    get debounceTime() {
        return this.debounce || 1000;
    }

    display(value){
        if (value == null) return this.emptyValue || "- нет -";
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

    getFilteredValues(text){
        if (!this.cache) return this.values;
        if (!this.values) return;
        if (!text) return this.values;

        if (this.$attrs["filter"]) {
            return this.values.filter(val => this.filter({$value:val,$text:text}));
        }

        return this.values.filter(val => {
            return val.toString()
                .toLowerCase()
                .indexOf(text.toLowerCase()) >= 0
        })
    }

    get isAllowClear(){
        if (this.allowEmpty === undefined) return false;
        return this.allowEmpty;
    }
}

export {SDDropdownComponentController as controller};