package com.zahir.fathurrahman.malite.app.service.anime.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.zahir.fathurrahman.malite.app.constant.Modules;
import com.zahir.fathurrahman.malite.app.model.helper.MALHTTPResponse;
import com.zahir.fathurrahman.malite.app.model.helper.node.MALNodeList;
import com.zahir.fathurrahman.malite.app.model.helper.QueryParameter;
import com.zahir.fathurrahman.malite.app.service.anime.AnimeService;
import com.zahir.fathurrahman.malite.app.service.helper.MALRequestService;
import com.zahir.fathurrahman.malite.core.exception.BadRequestException;
import com.zahir.fathurrahman.malite.core.model.BaseResponseData;
import com.zahir.fathurrahman.malite.core.util.CustomMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service @AllArgsConstructor @Slf4j
public class AnimeServiceImpl implements AnimeService {
    private MALRequestService malRequestService;

    @Override
    public BaseResponseData getAnime(HttpServletRequest request) {
        QueryParameter qp = CustomMapper.mapQueryParam(request, QueryParameter.class);
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
        QueryParameter qp = CustomMapper.mapQueryParam(request, QueryParameter.class);
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
    public BaseResponseData getAnimeRanking(HttpServletRequest request) {
        return null;
    }

    @Override
    public BaseResponseData getSeasonalAnime(HttpServletRequest request) {
        return null;
    }
}
