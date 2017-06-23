import {name as TranslateLoaderName} from "./translateLoader.factory"

TranslateConfig.$inject = ['$translateProvider'];
/**
 * Настройка angular translate
 */
export async function TranslateConfig($translateProvider){
    $translateProvider.useLoader(TranslateLoaderName);
    $translateProvider.preferredLanguage('ru');
    $translateProvider.useSanitizeValueStrategy('sanitizeParameters')
}