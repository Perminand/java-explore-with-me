package ru.practicum.ewm.main.common;

public class SetValuesEntity<T> {
    public static <T> void setValueIfNull(T params, T value) {
        if (params == null) {
            params = value;
        }
    }

    public static <T> void setValueIfNotNull(T params, T out, T value) {
        if (params != null) {
            out = value;
        }
    }

    public static <T> void setValueIfNull(T params, T value, T value2) {
        if (params == null) {
            params = value;
        } else {
            params = value2;
        }
    }
}
