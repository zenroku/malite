package com.zahir.fathurrahman.malite.app.service.anime.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.zahir.fathurrahman.malite.app.constant.AppConst;
import com.zahir.fathurrahman.malite.app.constant.Modules;
import com.zahir.fathurrahman.malite.app.constant.Tables;
import com.zahir.fathurrahman.malite.app.entity.RegisteredField;
import com.zahir.fathurrahman.malite.app.model.app.dto.AnimeDTO;
import com.zahir.fathurrahman.malite.app.model.app.qp.*;
import com.zahir.fathurrahman.malite.app.model.mal.MALHTTPResponse;
import com.zahir.fathurrahman.malite.app.model.mal.node.MALNodeList;
import com.zahir.fathurrahman.malite.app.model.mal.MALQueryParameter;
import com.zahir.fathurrahman.malite.app.repository.RegisteredFieldRepository;
import com.zahir.fathurrahman.malite.app.service.anime.AnimeService;
import com.zahir.fathurrahman.malite.app.service.helper.MALRequestService;
import com.zahir.fathurrahman.malite.app.service.helper.QueryParamService;
import com.zahir.fathurrahman.malite.core.exception.BadRequestException;
import com.zahir.fathurrahman.malite.core.model.BaseResponseData;
import com.zahir.fathurrahman.malite.core.util.CustomMapper;
import com.zahir.fathurrahman.malite.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@Service @AllArgsConstructor @Slf4j
public class AnimeServiceImpl implements AnimeService {
    private MALRequestService malRequestService;
    private JdbcTemplate jdbcTemplate;
    private RegisteredFieldRepository registeredFieldRepository;
    private QueryParamService queryParamService;

    @Override
    public BaseResponseData searchAnime(HttpServletRequest request) {
        MALQueryParameter qp = CustomMapper.mapQueryParam(request, MALQueryParameter.class);
        log.info("GET ANIME WITH PARAMETER : {} ",new Gson().toJson(qp));
        BaseResponseData baseResponse = new BaseResponseData();

        if (qp.getQ() == null) throw new BadRequestException("'q' query param is required");

        MALHTTPResponse malResponse = malRequestService.get("/anime", qp, Modules.GET_ANIME);
        if (malResponse.getResponseCode() == 200) {
            try {
                MALNodeList<Map<String,Object>> response = new ObjectMapper().readValue(malResponse.getData(),new TypeReference<>(){});
                baseResponse.setData(response.getData());
                baseResponse.setMessage("Success");
                return baseResponse;
            } catch (Exception e) {
                log.info("invalid response data of {}",malResponse.getData());
            }
        }

        baseResponse.setMessage(malResponse.getMessage());
        baseResponse.setSuccess(false);
        baseResponse.setStatus(malResponse.getResponseCode());

        return baseResponse;
    }

    @Override
    public BaseResponseData getAnimeDetail(HttpServletRequest request) {
        MALQueryParameter qp = CustomMapper.mapQueryParam(request, MALQueryParameter.class);
        log.info("GET ANIME DETAIL WITH PARAMETER : {} ",new Gson().toJson(qp));
        BaseResponseData baseResponse = new BaseResponseData();

        if (qp.getId() == null) throw new BadRequestException("'id' query param is required");

        MALHTTPResponse malResponse = malRequestService.get("/anime/" + qp.getId(), qp, Modules.GET_ANIME_DETAILS);
        if (malResponse.getResponseCode() == 200) {
            try {
                Map<String,Object> response = new ObjectMapper().readValue(malResponse.getData(),new TypeReference<>(){});
                baseResponse.setData(response);
                baseResponse.setMessage("Success");
                return baseResponse;
            } catch (Exception e) {
                log.info("invalid response data of {}",malResponse.getData());
            }
        }

        baseResponse.setMessage(malResponse.getMessage());
        baseResponse.setSuccess(false);
        baseResponse.setStatus(malResponse.getResponseCode());

        return baseResponse;
    }

    @Override
    public BaseResponseData getAnimeList(HttpServletRequest request) {
        BaseResponseData baseResponse = new BaseResponseData();
        ListQP qp = CustomMapper.listQP(request);
        log.info("GET ANIME LIST PARAMETER : {} ",new Gson().toJson(qp));

        qp.setTable(Tables.ANIME);
        qp.setFields(AppConst.GET_LIST_ANIME_FIELDS);
        List<RegisteredField> regField = registeredFieldRepository.findByKey(Tables.ANIME);
        qp.setRegFields(regField);
        QPStatement qps = queryParamService.queryBuilder(qp);
        String query = qps.getQuery();
        log.info("QUERY RESULT : {}",query);

        List<AnimeDTO> datas = jdbcTemplate.query(query, qps.getStatement(), (rs,idx)-> animeRSMapper(rs));

        baseResponse.setData(datas);

        return baseResponse;
    }

    private AnimeDTO animeRSMapper(ResultSet rs) {
        AnimeDTO dto = new AnimeDTO();
        for (String f : AppConst.GET_LIST_ANIME_FIELDS) {
            try {
                Field field = dto.getClass().getDeclaredField(StrUtil.snakeToCamel(f));
                field.setAccessible(true);
                if (field.getType().equals(Long.class)) {
                    field.set(dto, rs.getLong(f));
                } else if (field.getType().equals(Integer.class)) {
                    field.set(dto, rs.getInt(f));
                } else if (field.getType().equals(Double.class)) {
                    field.set(dto, rs.getDouble(f));
                } else {
                    field.set(dto,rs.getString(f));
                }
                field.setAccessible(false);
            } catch (Exception e){
                log.info("error on mapping value result set : {}",e);
            }
        }
        return dto;
    }
}
