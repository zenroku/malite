package com.zahir.fathurrahman.malite.app.service.anime.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zahir.fathurrahman.malite.app.model.anime.MALAnime;
import com.zahir.fathurrahman.malite.app.model.MALNodeList;
import com.zahir.fathurrahman.malite.app.model.QueryParameter;
import com.zahir.fathurrahman.malite.app.service.anime.GetAnimeService;
import com.zahir.fathurrahman.malite.core.config.AppVarConfig;
import com.zahir.fathurrahman.malite.core.model.BaseResponseArray;
import com.zahir.fathurrahman.malite.core.util.CustomMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@Service @AllArgsConstructor @Slf4j
public class GetAnimeServiceImpl implements GetAnimeService {
    private static final AppVarConfig appConfig = new AppVarConfig();

    private RestTemplate restTemplate;

    @Override
    public BaseResponseArray getAnime(HttpServletRequest request) {
        log.info("GET ANIME WITH PARAMETER : {} ",request.getParameterMap());
        BaseResponseArray baseResponse = new BaseResponseArray();
        QueryParameter qp = CustomMapper.mapQueryParam(request, QueryParameter.class);
        if (qp.getQ() != null){
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-MAL-CLIENT-ID",appConfig.getMAL_CLIENT_ID());
            HttpEntity httpEntity = new HttpEntity(null,headers);
            UriComponentsBuilder uriBuilderFactory = UriComponentsBuilder.fromHttpUrl(appConfig.getMAL_URL().concat("/anime"));
            uriBuilderFactory.queryParam("q",qp.getQ());
            uriBuilderFactory.queryParam("limit",qp.getLimit() != null ? qp.getLimit() : 5);

            try {

                ResponseEntity<String> response = restTemplate.exchange(uriBuilderFactory.build().encode().toUri(),HttpMethod.GET,httpEntity, String.class);
                MALNodeList<MALAnime> malAnimeMALNodeList = new ObjectMapper().readValue(response.getBody(), new TypeReference<>() {});
                baseResponse.setData(malAnimeMALNodeList.getData());
            } catch (Exception e){
                log.info("{}",e);
                baseResponse.setMessage("Failed to fetch data : " + e.getMessage());
            }
        } else {
            baseResponse.setMessage("'q' query param is required");
        }

        return baseResponse;
    }
}
