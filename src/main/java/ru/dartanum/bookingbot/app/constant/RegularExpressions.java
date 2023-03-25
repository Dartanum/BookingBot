package ru.dartanum.bookingbot.app.constant;

public interface RegularExpressions {
    String DATE_REGEXP = "^\\d{2}.\\d{2}.\\d{4}$";
    String DATE_RANGE_REGEXP = "^(\\d{2}.\\d{2}.\\d{4}){1}(\\s?-\\s?\\d{2}.\\d{2}.\\d{4})?$";
    String PHONE_REGEXP = "^\\+7\\d{10}$";
    String EMAIL_REGEXP = "^(.+)@(\\S+)$";
    String WORD_LIST_REGEXP = "^([а-яА-Я]{2,},\\s*)*([а-яА-Я]{2,}){1}$";
    String NAME_PART_REGEXP = "^[А-Я][а-я]+$";
    String SEX_REGEXP = "^[МЖ]$";
}
