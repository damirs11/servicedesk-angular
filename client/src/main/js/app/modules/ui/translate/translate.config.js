import {name as TranslateLoaderName} from "./translateLoader.factory"
import dictionary from "./dictionary.json";

TranslateConfig.$inject = ['$translateProvider'];
/**
 * Настройка angular translate
 */
export function TranslateConfig($translateProvider){
    $translateProvider.translations('ru', dictionary);
    $translateProvider.preferredLanguage('ru');
    $translateProvider.useSanitizeValueStrategy('sanitizeParameters')
}