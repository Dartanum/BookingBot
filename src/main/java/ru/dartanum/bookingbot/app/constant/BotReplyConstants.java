package ru.dartanum.bookingbot.app.constant;

public interface BotReplyConstants {
    String MSG_START = "Привет! Я - бот для поиска авиабилетов!";
    String MSG_INCORRECT_DATA = "Не могу разобрать... Повторите попытку";
    //---------------------------PASSENGER---------------------------
    String MSG_NO_PASSENGERS = "Кажется ты еще не добавил пассажиров :/";
    String MSG_NONEXISTENT_PASSENGER = "Такого пассажира нет";
    String MSG_ENTER_FULL_NAME = "Введи ФИО (через пробел)";
    String MSG_ENTER_SEX = "Введите пол: М / Ж";
    String MSG_ENTER_BIRTH_DATE = "Введите дату рождения в формате дд.мм.гггг";
    String MSG_DATE_AFTER_NOW = "Ты из будущего?!? 0.0";
    String MSG_ENTER_PHONE_NUMBER = "Введите номер телефона в формате: +79999999999";
    String MSG_ENTER_EMAIL = "Введите почту, на которую хотите получать билеты";
    String MSG_ENTER_CITIZENSHIP = "Перечислите через запятую гражданином каких стран Вы являетесь. Пример: Россия, США, Япония";
    String MSG_TEMPLATE_INVALID_COUNTRY_NAMES = "Распознанные страны были сохранены. Повторите попытку со следующими странами: %s";
    String MSG_TEMPLATE_PASSENGER_ADDED = "Добавлен пассажир:\n%s";
    String MSG_EDIT_PASSENGER_CHOOSE_FIELD = "Выберите что хотите изменить";
    String MSG_EDIT_PASSENGER_ENTER = "Введите новое значение";
    String MSG_SUCCESS_EDITED_PASSENGER = "Изменения успешно сохранены!";
    String MSG_ERROR_DURING_EDITING_PASSENGER = "Не удалось внести изменения :( Попробуй еще раз";
    //---------------------------TICKET---------------------------
    String MSG_NO_TICKETS = "У тебя еще нет билетов";
    //---------------------------BOOKING---------------------------
    String MSG_ASK_DATE_RANGE = "Введите дату поездки в формате дд.мм.гггг";
    String MSG_INVALID_DATE_RANGE = "Некорректная дата поездки";
    String MSG_CHOSE_SOURCE_AIRPORTS = "Выберите место отправления. Введите название аэропорта, города или страны";
    String MSG_CANNOT_FIND_AIRPORTS = "Я не смог найти совпадения :( Попробуй еще раз";
    String MSG_CHOOSE_DESTINATION_AIRPORT = "Выберите место назначения. Введите название аэропорта, города или страны";
    String MSG_START_SEARCHING_TICKETS = "Ищу билеты специально для вас!";
    String MSG_EMPTY_SEARCH_RESULT = "Не удалось найти ни одного билета по твоим параметрам :(";
    String MSG_TICKET_BOOKED = "Билет успешно забронирован!";
}
