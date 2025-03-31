package com.github.gaotianchi.shorten.utils;


import java.util.HashMap;
import java.util.Map;

/**
 * @author gaotianchi
 * @since 2025/3/24 下午2:53
 **/
public class Base62Converter {

    private static final Map<Integer, String> decTo62 = new HashMap<>();

    static {
        for (int i = 0; i < 10; i++) {
            decTo62.put(i, String.valueOf(i));
        }
        for (int i = 10; i < 36; i++) {
            decTo62.put(i, String.valueOf((char) ('A' + (i - 10))));
        }
        for (int i = 36; i < 62; i++) {
            decTo62.put(i, String.valueOf((char) ('a' + (i - 36))));
        }
    }

    public static String decToBase62(long num) {
        if (num == 0) {
            return decTo62.get(0);
        }

        StringBuilder result = new StringBuilder();
        int base = 62;

        while (num > 0) {
            int rem = (int) (num % base);
            result.insert(0, decTo62.get(rem));
            num = num / base;
        }

        return result.toString();
    }
}
