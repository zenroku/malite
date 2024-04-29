package com.zahir.fathurrahman.malite.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CustomMapper {
    public static <T> T mapQueryParam (HttpServletRequest request, Class<T> tClass) {
        Map<String,Object> qObj = new HashMap<>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            qObj.put(entry.getKey(), entry.getValue().length != 0 ? entry.getValue()[0] : null);
        }

        return new ObjectMapper().convertValue(qObj,tClass);
    }
}
