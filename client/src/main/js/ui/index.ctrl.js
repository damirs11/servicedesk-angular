import {Templates} from "./components/templates/Templates";
import {AuthTransition} from "./components/auth.transition";
import {XEditableSetup} from "./components/xeditable.setup";

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
        $injector.instantiate(XEditableSetup);

        // Настройка локали. В частности, используется в виджете "ui-grid"
        i18nService.setCurrentLang('ru');
    }
}

export {IndexController}