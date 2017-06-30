TranslateLoaderFactory.$inject = ["$http"];

/**
 * @deprecated все переводы хранятся в dictionary.json
 */
export function TranslateLoaderFactory($http){
    /**
     * Функция для подгрузки перевода по ссылке.
     * @param $http
     * @returns {TranslateLoader}
     * @constructor
     */
    return async function TranslateLoader(){
        const response = await $http.get('rest/service/config/getInfo');
        return response.data.translate;
    }
}

export const name = "TranslateLoader";