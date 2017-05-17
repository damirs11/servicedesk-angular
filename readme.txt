Инструкция по развертыванию проекта:

1. Для сборки и запуска проекта необходим JDK 1.8 x64 (:
		Cкачать с http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
2. Для сборки использовать Gradle 3.3
		Скачать с http://gradle.org/
3. Команда для сборки проекта:
		"gradle client:build server:war -i -g gradle"
4. Команда для запуска локального приложения:
		"gradle client:build server:appStart -i -g gradle"
			- Приложение будет доступно по адресу "https://localhost:8443/sd"
5. Обновление статического контента у запущенного приложения:
 		"gradle client:build"
5. Отладка на порту 5005 приложения выполняется командой:
        "gradle client:build server:appStartDebug"

Примечание:
        Флаги gradle:
        -i			вывод дополнительной информации в консоль о сборке
        -g gradle	путь для локального кэша gradle