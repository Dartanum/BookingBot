package ru.dartanum.bookingbot.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Storage {
    private static final Map<Long, BotState> botStateByUserId = new HashMap<>();
    private static final Map<Long, Map<String, Object>> variablesByUserId = new HashMap<>();

    private Storage() {}

    public static BotState getBotStateByUserId(Long userId) {
        return Objects.requireNonNullElse(botStateByUserId.get(userId), BotState.DEFAULT);
    }

    public static void updateBotState(Long userId, BotState newState) {
        botStateByUserId.put(userId, newState);
    }

    public static void putVariable(Long userId, String key, Object value) {
        var variables = variablesByUserId.get(userId);
        if (variables == null) {
            variablesByUserId.put(userId, new HashMap<>() {{put(key, value);}});
        } else {
            variables.put(key, value);
        }
    }

    public static Object getVariable(Long userId, String key) {
        var variables = variablesByUserId.get(userId);
        return variables == null ? null : variables.get(key);
    }
}
