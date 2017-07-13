## Введение
Все ajax запросы необходимо обвернуть методы классов в модуле API.
В коде других модулей не должны постоянно в разных местах встречаться адреса сервера.
Все вызовы ajax необходимо прописать соответсвующий класс. Например `SD.Servicecall` для заявок.
Поиск заявок: `SD.Servicecall.find({id:15})`

Также классы сущностей сервисдеска SD.%name% должны наследоваться от класса SD.Entity.
(Для чего и как - описано в `сущности.md`)

Некоторые важные методы можно вынести просто в SD: login, logout.


## Создание запросов
Для осуществления запросов необходимо использовать службу $connector.
Это наша настраеваемая обертка над $http, если ее методов недостаточно - можно расширить
основные:

* $connector.get(relativePath,params,timeout) - GET запрос
* $connector.post(relativePath,data,timeout) - POST запрос


## Использование async / await
Принцип async:
Если функция помечена с помощью async, то она всегда возвращает промис.
```JavaScript
async function getTen(){
  return 10;
}

console.log(getTen()); // логнет Promise {}
getTen().then((x) => console.log(x)) // логнет 10;
```
Если внутри async функции вернуть промис самостоятельно, то в then(value) попадет не сам промис
который мы возвращаем, а его значение.

В асинхронных функциях можно также использовать await. 
Его нужно применять на промисах. Это преостанавливает выполнение функции и ожидает значение от промиса.
Если же ожидаемый промис сделал reject - у нас выскочит ошибка. Поэтому некоторые await нужно оборачивать в
try-catch. 

###Примеры ajax запросов
```JavaScript

class Person extends Entity {
    
    static $inject = ["$connector"];
    
    async find(filter){
        const data = await $connector.get("/rest/ajax/entity/Person",null,filter)
        return Person.parse(data);
    }
}

```

```JavaScript
const $connector = null;

async function findPersons(){
    try { 
        const data = $connector.get("/rest/service/entity/Person",null,{firstName: "Иван"});
        return data.map(SD.Person.parse) // Превращаем их всех в персон
    } catch (error) {
        alert("Ошибка при загрузке персон")
    }
}
```