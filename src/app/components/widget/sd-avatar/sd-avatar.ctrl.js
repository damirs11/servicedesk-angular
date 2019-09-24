class SDAvatarController {

    static $inject = ["$attrs"];

    constructor($attrs){
        this.$attrs = $attrs;
    }

    get defaultType() {
        return this.$attrs.type === "default" ||
            this.$attrs.type == null
    }

    get typeClass() {
        let type = this.$attrs.type;
        if (!type) type = "default";
        const classMap = {};
        classMap[type] = true;
        return classMap;
    }

    get typeStyle() {
        if (this.defaultType) return {};
        return {width: this.size+'px', height: this.size+'px'};
    }
}

export {SDAvatarController as controller}