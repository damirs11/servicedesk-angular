Инструкция по развертыванию проекта:

1. Для сборки и запуска проекта необходим JDK 1.7 x64:
		Cкачать с http://www.oracle.com/technetwork/java/javase/downloads/jre7-downloads-1880261.html
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