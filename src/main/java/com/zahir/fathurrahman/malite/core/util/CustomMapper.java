package com.zahir.fathurrahman.malite.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zahir.fathurrahman.malite.app.constant.Modules;
import com.zahir.fathurrahman.malite.app.model.helper.QueryParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CustomMapper {
    public static <T> T mapQueryParam (HttpServletRequest request, Class<T> tClass) {
        Map<String,Object> qObj = new HashMap<>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            qObj.put(entry.getKey(), entry.getValue().length != 0 ? entry.getValue()[0] : null);
        }

        return new ObjectMapper().convertValue(qObj,tClass);
    }

    public static void setQueryParams(UriComponentsBuilder uri, QueryParameter qp, Modules module) {
        module.getAvailParams().forEach(e-> {
            try {
                Field field = qp.getClass().getDeclaredField(e);
                field.setAccessible(true);
                Object plainVal = field.get(qp);
                if (plainVal != null) {
                    uri.queryParam(e,plainVal);
                }
            } catch (Exception ex) {
                log.info("invalid param of {}\n",e,ex);
            }
        });
    }
}
