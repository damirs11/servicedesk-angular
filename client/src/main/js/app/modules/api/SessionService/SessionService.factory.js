SessionServiceFactory.$inject = ['USER_DATA', '$translate', '$state', '$http'];
export function SessionServiceFactory(USER_DATA, $translate, $state, $http) {
    /** Если текущий пользователь не авторизован, то перенаправляем его на форму ввода пароля */
    function checkUser() {
        if (USER_DATA.login === $translate.instant('default.login')) {
            $state.go("login")
        }
    }
    /** Запрашивает на сервере актуальную информацию о текущем пользователе */
    function checkSession() {
        $http.get('rest/service/config/getInfo').then(
            function (response) {
                angular.copy(response.data.user, USER_DATA);
                checkUser();
            }
        )
    }
    /** Осуществляет выход пользователя из системы */
    function logout() {
        $http.get('rest/service/security/logout').then(
            checkSession
        );
    }
    return {
        checkUser: checkUser,
        checkSession: checkSession,
        logout: logout
    }
};