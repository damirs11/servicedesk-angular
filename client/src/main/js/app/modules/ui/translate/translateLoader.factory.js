TranslateLoaderFactory.$inject = ["$http"];

export function TranslateLoaderFactory($http){
    return async function TranslateLoader(){
        const response = await $http.get('rest/service/config/getInfo');
        return response.data.translate;
    }
}

export const name = "TranslateLoader";