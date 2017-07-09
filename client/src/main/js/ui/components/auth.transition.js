AuthTransition.$inject = ['$transitions', 'SD', '$state', '$trace'];
function AuthTransition($transitions, SD, $state, $trace) {

    // Для отладки: Включаем логирование переходов между страницами
    $trace.enable('TRANSITION');

    /**
     * Проверка, что можем выполнить переход по указанному стейту.
     */
    $transitions.onBefore({
        to: state => state.data && state.data.needAuthorize && !SD.authorized
    }, (transition) => {
        return $state.target('app.login');
    });
}

export {AuthTransition}