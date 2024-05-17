package com.zahir.fathurrahman.malite.core.util;

import java.util.List;

public class StrUtil {
    public static boolean isNull(Object str){
        String strv = String.valueOf(str);
        List<String> nullVals = List.of("","null","nil");
        return nullVals.contains(strv);
    }
    public static String camelToSnake(String str){
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        str = str.replaceAll(regex,replacement).toLowerCase();
        return str;
    }
    public static String snakeToCamel(String str)
    {
        // Capitalize first letter of string
        str = str.substring(0, 1).toUpperCase()
                + str.substring(1);

        while (str.contains("_")) {
            str = str
                    .replaceFirst(
                            "_[a-z]",
                            String.valueOf(
                                    Character.toUpperCase(
                                            str.charAt(
                                                    str.indexOf("_") + 1))));
        }

        // Return string
        return str;
    }
}
