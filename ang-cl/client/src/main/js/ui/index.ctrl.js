import {Templates} from "./components/templates/Templates";
import {AuthTransition} from "./components/auth.transition";
import {moment} from "../common/web-libraries.const"

/**
 * Основной контроллер приложения.
 * Используется для конфигурации других модулей без module.run()
 * и управления файлом index.html
 */
class IndexController {

    static $inject = ['$injector', 'i18nService'];

    constructor($injector, i18nService){
        $injector.instantiate(Templates);
        $injector.instantiate(AuthTransition);

        // Настройка локали. В частности, используется в виджете "ui-grid"
        i18nService.setCurrentLang('ru');
        moment.locale("ru")
    }
}

export {IndexController}