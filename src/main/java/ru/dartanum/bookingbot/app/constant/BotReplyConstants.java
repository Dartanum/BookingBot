package ru.dartanum.bookingbot.app.constant;

public interface BotReplyConstants {
    String MSG_START = "Привет! Я - бот для поиска авиабилетов!";
    String MSG_TICKET_SEARCHING = "Ищу билеты...";
    String MSG_ASK_DATE_RANGE = "Укажите даты начала и конца поездки в формате дд.мм.гггг - дд.мм.гггг (дата конца необязательна)";
    String MSG_INVALID_BOOKING_DATE_FORMAT = "Не могу разобрать... Попробуй еще раз (верный формат: дд.мм.гггг - дд.мм.гггг)";
}
