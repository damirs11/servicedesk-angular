/**
 * Фильтр сущностей
 * @class
 * @name UIEntityFilter
 */
class UIEntityFilter {

    /**
     * Название фильтра
     * @property
     * @name UIEntityFilter#name
     * @type {string}
     */
    name;

    /**
     * Значение фильтра
     * @property
     * @name UIEntityFilter#value
     * @type {string|undefined}
     */
    value;

    /**
     * Дочерние фильтры.
     * @property
     * @name UIEntityFilter#childs
     * @type {UIEntityFilter[]|undefined}
     */
    childs;

    /**
     * Фильтр-разделительная черта.
     * @property
     * @name UIEntityFilter#childs
     * @type {boolean}
     */
    divider;

    /**
     * Загловок.
     * @property
     * @name UIEntityFilter#header
     * @type {string}
     */
    header;

    get clickable(){
        return this.value || !this.childs && !this.divider
    }

    get hasChilds(){
        return this.childs && Boolean(this.childs.length)
    }

    /**
     * Конструктор фильтра.
     * Использование:
     * new UIEntityFilter(name: string, value: string) - фильтр с названием и значением
     * new UIEntityFilter(name: string, options: object)- фильтр с названием и конфигруацией
     * new UIEntityFilter(name: object) - фильтр с конфиграцией.
     */
    constructor(name, opts) {
        if (typeof name === "string" && typeof opts === "string") {
            this.name = name;
            this.value = opts;
            return;
        }

        if (typeof name === "object") {
            opts = name;
            name = opts.name;
        }
        this.name = name;
        this.value = opts.value;
        this.childs = opts.childs;
        this.divider = Boolean(opts.divider);
        this.header = opts.header;
    }

}

export {UIEntityFilter};