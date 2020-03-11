[![Build Status](https://travis-ci.org/BischevRamil/job4j_tracker.svg?branch=master)](https://travis-ci.org/BischevRamil/job4j_tracker)


[![codecov](https://codecov.io/gh/BischevRamil/job4j_tracker/branch/master/graph/badge.svg)](https://codecov.io/gh/BischevRamil/job4j_tracker)


# Tracker

Консольное приложение Tracker для хранения заявок.
Заявка имеет поля id, name, description, time.
Имеет следующий функционал:
1. Ссоздать заявку.
2. Найти заявку по имени, по идентификатору.
3. Редактировать заявку.
4. Удалить заявку.
5. Показать все заявки.

Заявки хранятся в СУБД PostgreSQL.
Поддерживается схема Liquibase.
Приложение интегрировано в CI/CD Travis.
Код покрыт тестами с использованием библиотеки JUnit.
Имеется логирование ошибок с помощью библиотеки Log4j.
