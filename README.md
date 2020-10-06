#Страница авторизации Альфа-банк
Тестирование авторизации в системе интернет-банка Альфа-банк.

##Начало работы
Скопировать через GIT на свой ПК проект командой git clone https://github.com/larinasof/sql.

##Prerequisites
На ПК необходимо установить: OpenJDK 11, Git, IntelliJ IDEA, Docker/Docker Toolbox, браузер.

##Установка и запуск
Команды необходимо выполнить в терминале проекта в IntelliJ IDEA.

1. Запустить docker-контейнер командой `docker-compose up`.
2. Выполнить команду `docker-compose exec mysql mysql -u user database -p`, далее ввести пароль `pass`.
3. Проверить наличие таблицы командой `show tables;`.
4. Запустить SUT, выполнив команду `java -jar artifacts\app-deadline.jar -P:jdbc.url=jdbc:mysql://192.168.99.100:3306/database -P:jdbc.user=user -P:jdbc.password=pass`.
5. Запустить выполнение тестов.

Для повторного использования тестов необходимо перезапустить SUT.