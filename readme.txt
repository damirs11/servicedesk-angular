Инструкция по развертыванию проекта:

1. Для сборки и запуска проекта необходим JDK 1.7 x64:
		Cкачать с http://www.oracle.com/technetwork/java/javase/downloads/jre7-downloads-1880261.html
2. Для сборки использовать Gradle 3.3
		Скачать с http://gradle.org/
3. Команда для сборки проекта:
		"gradle build" или "gradle build -Porg.gradle.java.home=%JAVA_HOME_1_7%"
4. Команда для запуска локального приложения:
		"gradle appRunWar
			- Приложение будет доступно по адресу "https://localhost:8443/sd"
5. Отладка приложения после локального запуска осуществляется на порту 5005