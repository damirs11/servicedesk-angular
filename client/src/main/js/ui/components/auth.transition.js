AuthTransition.$inject = ['$transitions', 'Session', '$state', '$trace',"$location"];
function AuthTransition($transitions, Session, $state, $trace, $location) {

    // Для отладки: Включаем логирование переходов между страницами
    if ('GULP_REPLACE:DEBUG') $trace.enable('TRANSITION');

    const criteriaNeedAuthorize = {
        entering: state => state.data && state.data.needAuthorize
    };
    /**
     * Проверка, что можем выполнить переход по указанному стейту.
     */
    $transitions.onEnter(criteriaNeedAuthorize, (transition) => {
        if (!Session.authorized) {
            const target = transition.targetState();
            const returnUrl = $state.href(target.name(), target.params());
            return $state.target('app.login',{returnUrl});
        }
    });

    const criteriaToLoginState = {
        to: "app.login"
    };
    $transitions.onEnter(criteriaToLoginState, (transition) => {
        if (Session.authorized) return $state.target('app.main');
    });
}

export {AuthTransition}