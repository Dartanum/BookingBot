package ru.dartanum.bookingbot.app.constant;

public interface CallbackActionConstants {
    String PARAM_DELIMITER = ",";
    //-----------------------------ACTIONS-----------------------------
    String ACT_EDIT_PASSENGER = "startEdit:";
    String ACT_DELETE_PASSENGER = "delete:";
    String ACT_EDIT_PASSENGER_CHOOSE_FIELD = "edit:";
    String ACT_BOOKING_CHOOSE_VARIANT = "chooseVariant:";
    String ACT_START_TICKET_BOOKING = "startBooking:";
    //-----------------------------PARAMS-----------------------------
    String[] EDIT_PASSENGER_FIELDS = {"name", "surname", "fatherName", "phone", "email", "birthDate", "sex", "citizenship"};
}
