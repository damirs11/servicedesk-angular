AuthTransition.$inject = ['$transitions', 'Session', '$state', '$trace',"$location"];
function AuthTransition($transitions, Session, $state, $trace, $location) {

    // Для отладки: Включаем логирование переходов между страницами
    if ('GULP_REPLACE:DEBUG') $trace.enable('TRANSITION');

    /**
     * Проверка, что можем выполнить переход по указанному стейту.
     */
    $transitions.onEnter({
        entering: state => state.data && state.data.needAuthorize
    }, (transition) => {
        const returnUrl = $state.href(transition.$to());
        if (!Session.authorized) {
            return $state.target('app.login',{returnUrl});
        }
    });

    $transitions.onEnter({
        to: "app.login"
    }, (transition) => {
        if (Session.authorized) return $state.target('app.main');
    });
}

export {AuthTransition}