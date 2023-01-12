package com.zahir.fathurrahman.malite.app.get_anime.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zahir.fathurrahman.malite.app.get_anime.model.GetAnimeDatas;
import com.zahir.fathurrahman.malite.core.config.AppVarConfig;
import com.zahir.fathurrahman.malite.core.model.BaseResponse;
import com.zahir.fathurrahman.malite.core.model.BaseResponseArray;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service @AllArgsConstructor
public class GetAnimeServiceImpl implements GetAnimeService {
    private static final AppVarConfig appConfig = new AppVarConfig();

    private RestTemplate restTemplate;

    @Override
    public BaseResponseArray getAnime(HttpServletRequest request) {
        BaseResponseArray baseResponse = new BaseResponseArray();
        Optional<String> search = Optional.ofNullable(request.getParameter("search"));
        if (search.isPresent()){
            Optional<String> limit = Optional.ofNullable(request.getParameter("limit"));
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-MAL-CLIENT-ID",appConfig.getMAL_CLIENT_ID());
            HttpEntity httpEntity = new HttpEntity(null,headers);
            UriComponentsBuilder uriBuilderFactory = UriComponentsBuilder.fromHttpUrl(appConfig.getMAL_URL().concat("/anime"));
            uriBuilderFactory.queryParam("q",search.get());
            uriBuilderFactory.queryParam("limit",limit.isPresent() ? limit.get() : 5);

            try {
                ResponseEntity response = restTemplate.exchange(uriBuilderFactory.build().encode().toUri(),HttpMethod.GET,httpEntity, Map.class);
                if (response.getBody() != null){
                    GetAnimeDatas result = new GetAnimeDatas(response);
                    baseResponse.setData(result.getAnimeMap());
                }
            } catch (Exception e){
                baseResponse.setMessage("Failed to fetch data : " + e.getMessage());
            }
        }

        return baseResponse;
    }
}
