package com.stelch.games2.core.Utils;

public class JavaUtils {
    public static <T> T getFromArrayOrNull(T[] array, int index) {
        if ((index >= 0) && (index < array.length)) {
            return array[index];
        } else {
            return null;
        }
    }
}
