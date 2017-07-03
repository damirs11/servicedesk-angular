import {AppState} from "./app/app.state";
import {MainState} from "./app.main/main.state";
import {LoginState} from "./app.login/login.state";
import {UserState} from "./app.user/user.state";

StateConfig.$inject = ["$stateProvider","$urlRouterProvider"];
function StateConfig($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/");
    $stateProvider
        .state(AppState)
        .state(MainState)
        .state(LoginState)
        .state(UserState)
}

export {StateConfig};