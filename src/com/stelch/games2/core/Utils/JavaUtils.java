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
    public static String center(String str, int size) {
        int left = (size - str.length()) / 2;
        int right = size - left - str.length();
        String repeatedChar = " ";
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < left; i++) {
            buff.append(repeatedChar);
        }
        buff.append(str);
        for (int i = 0; i < right; i++) {
            buff.append(repeatedChar);
        }
        return (buff.toString());
    }

    public static int chatColorCount(String someString,char searchedChar,int index){if(index>=someString.length()){return 0;}
        int count=someString.charAt(index)==searchedChar?1:0;return (count+chatColorCount(someString,searchedChar,index+1));}
}
