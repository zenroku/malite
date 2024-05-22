package com.zahir.fathurrahman.malite.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zahir.fathurrahman.malite.app.constant.AppConst;
import com.zahir.fathurrahman.malite.app.constant.Modules;
import com.zahir.fathurrahman.malite.app.model.app.qp.ListQP;
import com.zahir.fathurrahman.malite.app.model.mal.MALQueryParameter;
import com.zahir.fathurrahman.malite.core.exception.BadRequestException;
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

    public static ListQP listQP(HttpServletRequest request) {
        try {
            ListQP result = new ListQP();
            ObjectMapper om = new ObjectMapper();
            Map<String,String[]> param = request.getParameterMap();
            if (param.get(AppConst.FILTERS) != null) {
                result.setFilters(om.readValue(param.get(AppConst.FILTERS)[0], new TypeReference<>() {}));
            }
            if (param.get(AppConst.SORTS) != null) {
                result.setSorts(om.readValue(param.get(AppConst.SORTS)[0], new TypeReference<>() {}));
            }
            if (param.get(AppConst.LIMIT) != null) {
                result.setLimit(MALDataConverter.itg(param.get(AppConst.LIMIT)[0]));
            }
            if (param.get(AppConst.PAGE) != null) {
                result.setPage(MALDataConverter.itg(param.get(AppConst.PAGE)[0]));
            }

            return result;
        } catch (Exception e) {
            log.info("{}",e);
            throw new BadRequestException("Invalid query parameters");
        }
    }

    public static void setQueryParams(UriComponentsBuilder uri, MALQueryParameter qp, Modules module) {
        module.getAvailParams().forEach(e-> {
            try {
                Field field = qp.getClass().getDeclaredField(e);
                field.setAccessible(true);
                Object plainVal = field.get(qp);
                if (plainVal != null) {
                    uri.queryParam(StrUtil.camelToSnake(e),plainVal);
                }
                field.setAccessible(false);
            } catch (Exception ex) {
                log.info("invalid param of {}\n",e,ex);
            }
        });
    }
}
