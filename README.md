# Wordle 
[![Build Status](https://app.travis-ci.com/DanilKozlov00/Wordle.svg?token=YjJ7dvZ32iyxGEscJx5P&branch=master)](https://app.travis-ci.com/DanilKozlov00/Wordle)
[![codecov](https://codecov.io/gh/DanilKozlov00/Wordle/branch/junit5/graph/badge.svg?token=03dGqL07wb)](https://codecov.io/gh/DanilKozlov00/Wordle)
# Задание 1. Java Core. #

Разработать прототип игры Wordle. Суть игры есть секретное слово из пяти букв, его надо угадать за шесть попыток. После каждого хода компьютер указывает находится ли буква на правильном месте, не правильном, отсутствует в слове. Важно, что попытки должны быть нормальными словами, а не случайными последовательностями типа «аоеиу». 
	
Игра должна обладать следующим функционалом:  
  >1)Ввод существующих слов и проверка их с загаданным словом.  
  >2)По окончанию каждого раунда указывать корректное\некорректное положение буквы, ее отсутствие в слове.  
  >3)Интерфейс игры должен использовать только латинские буквы и цифры.  
  
Должно быть предоставлено консольное приложение для работы с игрой:  
  >1)Метод отображения правильного\неправильного\отсутствия положения буквы в введенном слове остается за разработчиком, однако он должен быть интуитивно понятен.  
Альтернативным вариантом может быть приложение, не предоставляющее интерфейс и корректность работы которого определяется разработанным набором тестов.  
  >1)Выбор метода и технологии тестирования остается за разработчиком.  
  >2)Реализованный набор тестов должен в достаточной мере обрабатывать возможные действия пользователя.  

# Задание 2. Spring Core. #
 >1)Переписать реализацию задания 1 на технологии spring.
# Задание 3. Spring Web + DB. #
## Задание 3.1. Spring Web. ##
 >1)Заменить консольный клиент на web + rest api интерфейс.
## Задание 3.2. DB. ##
 >1)Обеспечить сохранение результатов всех игр в базе данных в виде общей статистики.
## Задание 3.3. Security. ##
 >1)Добавить возможность регистрации, авторизации при помощи Spring security.  
 >2)Изменить метод сохранения статистики, с общего, на закрепленного за пользователем.  
 >3)Реализовать страницу просмотра рейтинга пользователей.
