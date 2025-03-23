package com.example.rest_thread;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    // Email Pattern
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static Pattern pattern;
    private static Matcher matcher;

    /**
     * Проверка на Email чрез Регулярен израз
     * @param email - Email за проверка
     * @return true ако е валиден, false ако е невалиден
     */
    public static boolean validate(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();
    }

    /**
     * Проверка за Null String object
     * @param txt - Входен стринг
     * @return true ако не е null и не е празен, иначе false
     */
    public static boolean isNotNull(String txt) {
        return txt != null && txt.trim().length() > 0;
    }
}
