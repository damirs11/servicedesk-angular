/**
 * Данный класс расширяет стандартный список. Дополнительное поле
 * с общим количеством предначено для работы с постраничным
 * отображением данных и содержит информацию об общем числе
 * записей помимо тех, что были запрошены для конкретной страницы.
 */
class PagingList{

    constructor(list, total) {
        this.list = list;
        this.total = total ? total : list.length;
    }

}

export {PagingList}