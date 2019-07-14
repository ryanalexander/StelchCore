/*
 *
 * *
 *  *
 *  * © Stelch Games 2019, distribution is strictly prohibited
 *  *
 *  * Changes to this file must be documented on push.
 *  * Unauthorised changes to this file are prohibited.
 *  *
 *  * @author Ryan Wood
 *  * @since 14/7/2019
 *
 */

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
