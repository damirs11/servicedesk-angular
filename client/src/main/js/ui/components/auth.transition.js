AuthTransition.$inject = ['$transitions', 'Session', '$state', '$trace'];
function AuthTransition($transitions, Session, $state, $trace) {

    // Для отладки: Включаем логирование переходов между страницами
    $trace.enable('TRANSITION');

    /**
     * Проверка, что можем выполнить переход по указанному стейту.
     */
    $transitions.onEnter({
        entering: state => state.data && state.data.needAuthorize
    }, (transition) => {
        if (!Session.authorized) {
            return $state.target('app.login');
        }
    });

    $transitions.onEnter({
        to: "app.login"
    }, (transition) => {
        if (Session.authorized) return $state.target('app.main');
    });
}

export {AuthTransition}