SDResolver.$inject = ["SD"];
/**
 * Функция предназначена для получения SD с новым, дочерним кэшем.
 * Используется как резолвер в стейтах.
 * @param SD {SD}
 */
function SDResolver(SD) {
    return SD.withCache();
}

export {SDResolver};