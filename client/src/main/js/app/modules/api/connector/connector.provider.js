import {Connector} from "connector";

ConnectorProvider.$inject = ["$injector"];
export function ConnectorProvider($injector){

    let address = null; // Если null - используется тот же, где находится front

    return {
        setAddress(addr){
            address = addr
        },
        $get(){
            return $injector.instantiate(Connector, {config: {
                address: address
            }})
        }
    }
}