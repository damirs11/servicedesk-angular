import {Connector} from "./connector";

// $http нужен внутри Connector'а
export function ConnectorProvider(){

    let address = "/sd";

    const provider =  {
        setAddress(addr){
            address = addr
        },
        $get($injector){
            return $injector.instantiate(Connector, {config: {
                address: address
            }})
        }
    };
    provider.$get.$inject = ["$injector"];
    return provider;
}