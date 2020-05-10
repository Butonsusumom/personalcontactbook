[![Build Status](https://travis-ci.org/VadimSharomov/phone-book.svg?branch=master)](https://travis-ci.org/VadimSharomov/phone-book)

Technologies: RESTful Web Service, Spring Boot, Spring Security, Spring Data JPA, MySql, H2, XML, JSON, Junit, Mockito, RegExp, JS, JQuery, HTML, Bootstrap, Thymeleaf.


Web проект “Телефонная книга”.
Язык программирования: Java
Инструменты:
JDK             (http://java.sun.com)
Spring          (http://spring.io/)
Maven           (https://maven.apache.org/)

Хранимые данные
Информация о пользователе в системе
Логин (только английские символы, не меньше 3х, без спецсимволов)
Пароль (минимум 5 символов)
ФИО (минимум 5 символов)

Хранимая информация для пользователя системы (одна запись)
Фамилия (обязательный, минимум 4 символа)
Имя (обязательный, минимум 4 символа)
Отчество (обязательный, минимум 4 символа)
Телефон мобильный (обязательный)
Телефон домашний (не обязательный)
Адрес (не обязательный)
e-mail (не обязательный, общепринятая валидация)

Содержит страницы:
Авторизация:
Вход в систему (логин/пароль)
Регистрация
Выход из системы

Работа с хранимыми данными
Просмотр всех данных с возможностью фильтрации по имени/фамилии и номеру телефона (не полное соответствие).
Добавление/Редактирование/Удаление хранимых записей
        Система доступна только авторизованным пользователям.
		Если пользователь не авторизован, при попытке открытия любой страницы его перенаправляет на страницу авторизации.
		На странице авторизации он может ввести логин и пароль для входа в систему или зарегистрироваться.
		При регистрации указываются поля: ФИО, логин и пароль.
        У каждого авторизованного пользователя имеется своя телефонная книга, т.е. каждый пользователь видит только те записи, которые он создал.

Формат телефонов проверяется и является валидным для Украины, пример: +380(66)1234567
Приложение содержат JUnit тесты с использованием Mockito.
Проект собирается средствами Maven
Для запуска используется SpringBoot

Все настройки приложения должны находится в properties файле (application.properties, config.properties).
В конфигурационном файле указывается тип хранилища (mysql, H2, xml, json)
Тип хранилища используется один раз при старте JVM (изменения в конфигурационном файле вступают в силу только при перезапуске JVM).
Реализовано четыре варианта хранилища: СУБД (MySQL или H2 в файловом режиме) или файл-хранилище (XML или JSON).
Настройки хранилища указываются в файле-конфигурации application.properties, config.properties (хост и пользователь для СУБД, путь к файлу для файлового хранилища).
Для файлового хранилища - в случае отсутствия файлов - они создаются автоматически.
Для СУБД-хранилища в файле README.md (ниже под описанием) находится SQL запрос для создания всех необходимых таблиц.


CREATE DATABASE IF NOT EXISTS phone_book;

CREATE USER 'User'@'localhost' IDENTIFIED BY 'password';

CREATE TABLE IF NOT EXISTS phone_book.user (id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, fullname varchar(45), login varchar(45), password varchar(100), role varchar(45), INDEX(login));

CREATE TABLE IF NOT EXISTS phone_book.contact (id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, iduser BIGINT NOT NULL, lastname varchar(45), name varchar(45), middlename varchar(45), mobilephone varchar(45), homephone varchar(45), address varchar(45), email varchar(45), INDEX(lastName));
