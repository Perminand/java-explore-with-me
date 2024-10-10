https://github.com/Perminand/java-explore-with-me/pull/5

## Описание

Backend часть приложения для формирования афиши событий и поиска друзей для совместного посещения мероприятий

Проект разделен на четыре модуля, поддерживая микросервисную архитектуру:

1. Сервер основного приложения, предоставляющего главный функционал.
2. Сервер статистики, хранящий в себе информацию необходимую для анализа и формирования просмотров событий
3. Отдельный модуль для хранения шаблонных DTO (Data transfer object)
4. Http клиент

Для взаимодействия модулей по REST API реализован HTTP-client с помощью WebClient.

## Технологии и инструменты

* Java core
* SpringBoot + SpringJpa + Hibernate
* Maven - управление зависимостями, многомодульность
* Docker - развертывание и контейнеризация
* СУБД - postgresSQL
* Lombok
* WebClient

## Цель

Учебный проект, направленный на освоение использованных технологий

## Функционал

Публичный API приложения позволяет не авторизованным пользователям искать события по фильтрам, просматривать события,
просматривать категории, получать подборки событий, используя фильтрацию и сортировку.

Приватный API дает возможность авторизованным пользователям создавать или обновлять события,
просматривать пользовательские события, просматривать и принимать заявки на участие.
Также, авторизованные пользователи могут
создавать заявки на участие в чужих событиях.
Также, реализован функционал оставления комментария к событиям.

API для администрирования определяет управление пользователями, подборками и категориями.

## Запуск

1. Клонировать репозиторий: git clone https://github.com/Perminand/java-explore-with-me.git
2. Перейти в папку с проектом cd java-explore-with-me
3. Запуск: docker compose up -d