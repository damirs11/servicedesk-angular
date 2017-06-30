class UserController {
    static $inject = ['SD', '$state'];

    constructor(SD, $state) {
        if (!SD.authorized) $state.go("app.login");
    }
}

export {UserController}