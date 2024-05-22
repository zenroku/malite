package com.zahir.fathurrahman.malite.app.service.helper.impl;

import com.zahir.fathurrahman.malite.app.constant.Modules;
import com.zahir.fathurrahman.malite.app.model.mal.MALHTTPResponse;
import com.zahir.fathurrahman.malite.app.model.mal.MALQueryParameter;
import com.zahir.fathurrahman.malite.app.service.helper.MALRequestService;
import com.zahir.fathurrahman.malite.core.config.AppVarConfig;
import com.zahir.fathurrahman.malite.core.util.CustomMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
@AllArgsConstructor
public class MALRequestServiceImpl implements MALRequestService {
    private static final AppVarConfig appConfig = new AppVarConfig();
    private RestTemplate restTemplate;
    @Override
    public MALHTTPResponse get(
            String uri,
            MALQueryParameter qp,
            Modules module
    ) {
        MALHTTPResponse response = new MALHTTPResponse();

        // setting headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-MAL-CLIENT-ID",appConfig.getMAL_CLIENT_ID());
        // preparing http entity
        HttpEntity httpEntity = new HttpEntity(null,headers);
        // preparing URI
        UriComponentsBuilder uriBuilderFactory = UriComponentsBuilder.fromHttpUrl(appConfig.getMAL_URL().concat(uri));
        CustomMapper.setQueryParams(uriBuilderFactory,qp, module);
        try {
            log.info("GETTING DATA OF MODULE {} to MAL...",module.name());
            // FIRE Http CURL GET METHOD to MAL
            ResponseEntity<String> httpResp = restTemplate.exchange(uriBuilderFactory.build().encode().toUri(), HttpMethod.GET,httpEntity, String.class);
            log.info("FINISH GET DATA FROM MAL, mapping up results...");
            // mapping response
            response.setResponseCode(httpResp.getStatusCode().value());
            response.setMessage(httpResp.getStatusCode().name());
            response.setData(httpResp.getBody());
        } catch (Exception e){
            log.info("{}",e);
        }

        return response;
    }
}
