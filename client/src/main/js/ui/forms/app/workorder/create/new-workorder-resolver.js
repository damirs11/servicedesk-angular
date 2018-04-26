function NewWorkorderResolver(SD) {
    const workorder = new SD.Workorder();
    workorder.$updateRaw({assignment: new SD.EntityAssignment()});
    return workorder;
}
NewWorkorderResolver.$inject = ["SD"];

export {NewWorkorderResolver};