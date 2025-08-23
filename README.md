# Дипломный проект по профессии «Инженер по тестированию»
Проект по автоматизации тестирования веб сервиса по покупке тур предложения "Путешествие дня" дебетовой картой и в кредит.

[**План автоматизации**](https://github.com/Vera1744/diplom/blob/main/docs/plan.md)

[**Отчёт**](https://github.com/Vera1744/diplom/blob/main/docs/report.md)

## Начало работы
1. Зарегистрируйтесь на GitHub
2. Сделайте fork [проекта](https://github.com/Vera1744/diplom).
2. Создайте директорию будущего проекта на локальном ПК.
3. Откройте командную строку для этой директории и инициализируйте слежение Git командой: `git init`.
5. В командную строку введите команду:  `git remote add origin git@github.com:Vera1744/diplom.git`
6. Склонируйте удаленный репозиторий - в командной строке введите команду `git clone git@github.com:Vera1744/diplom.git`

### Prerequisites

Необходимое окружение:

* Git
* Браузер Google Chrome
* IntelliJ IDEA
* Docker Desktop


### Установка и запуск

1. Запустите Docker Desktop
2. Откройте проект в Intellij IDEA 
3. Введите команду `docker-compose up` для запусков контейнеров, убедитесь, что они запустились. 
4. Запустите jar-файл, в новом терминале введите команду, для `MySQL`: `java -jar artifacts/aqa-shop.jar` для `PostgreSQL`: `java -jar .\artifacts\aqa-shop.jar -Dspring.datasource.url=jdbc:postgressql://localhost:5432/app`
5. Откройте в браузере [приложение](http://localhost:8080/), убедитесь, что оно запускается и работает.
6. Запусите тесты. Вернитесь в IntelliJ IDEA и в новом терминале введите команду для `MySQL`:`./gradlew clean test` для `PostgreSQL`:`./gradlew clean test "-Ddb.url=jdbc:postgresgl://localhost:5432/app"`
### Окончание работы
1. Остановить SUT командой CTRL + C
2. По окончанию работы становите работу контейнеров командой `docker-compose down`

### Просмотреть отчет
Для получения отчета Allure в терминале введите команду: `./gradlew allureServe`