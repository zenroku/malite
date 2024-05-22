package com.zahir.fathurrahman.malite.core.util;

import java.util.List;
import java.util.regex.Pattern;

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
        str = Pattern.compile("_([a-z])")
                .matcher(str)
                .replaceAll(m -> m.group(1).toUpperCase());

        return str;
    }
}
