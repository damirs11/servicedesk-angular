
AuthTransition.$inject = ["$transitions","SD","$state"];
function AuthTransition($transitions,SD,$state){
    $transitions.onBefore({
        to: state => state.data && state.data.needAuthorize && !SD.authorized
    },(transition) => {
        return $state.target("app.login")
    });
}

export {AuthTransition}