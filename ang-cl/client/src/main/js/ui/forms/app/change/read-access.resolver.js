function ReadAccessResolver(Session) {
    const val = Session.getTypeAccessRules("Change").isReadEntityAllowed;
    if (!val) throw "Read entity not allowed";
    return val;
}
ReadAccessResolver.$inject = ["Session"];

export {ReadAccessResolver}