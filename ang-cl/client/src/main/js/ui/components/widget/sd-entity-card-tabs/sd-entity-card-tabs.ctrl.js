class SDEntityCardTabsController {

    tabDisabled(tab) {
        if (!tab.disabled) return false;
        if (typeof tab.disabled == "function") {
            return tab.disabled()
        } else {
            return tab.disabled
        }
    }

    tabEnabled(tab) {
        return !this.tabDisabled(tab)
    }
}

export {SDEntityCardTabsController as controller}