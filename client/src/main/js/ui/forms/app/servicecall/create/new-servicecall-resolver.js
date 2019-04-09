// Новая заявка внедряется зависимостью.
// Все для того, чтобы передавать без проблем его между дочерними стейтами.
NewServiceCallResolver.$inject = ["SD"];
function NewServiceCallResolver(SD) {
    const serviceCall = new SD.ServiceCall();
    serviceCall.$update({
        assignment: new SD.EntityAssignment(),
    });
    return serviceCall;
}

export {NewServiceCallResolver};