import {Templates} from "./components/templates/Templates";
import {AuthTransition} from "./components/auth.transition";

/**
 * Основной контроллер приложения.
 * Используется для конфигурации других модулей без module.run()
 * и управления файлом index.html
 */
class IndexController {

    static $inject = ["$injector"];

    constructor($injector){
        $injector.instantiate(Templates);
        $injector.instantiate(AuthTransition)
    }
}

export {IndexController}