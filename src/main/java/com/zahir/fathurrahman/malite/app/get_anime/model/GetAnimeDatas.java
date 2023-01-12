package com.zahir.fathurrahman.malite.app.get_anime.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetAnimeDatas {
    private final List<GetAnimeData> anime = new ArrayList<>();
    private List<Map<String,Object>> nodeObj;
    private final List<Map<String,Object>> animeMap = new ArrayList<>();

    public GetAnimeDatas(ResponseEntity responseBody){
        Map<String, Object> mappedResponse = new ObjectMapper().convertValue(responseBody.getBody(),Map.class);
        nodeObj = (List<Map<String,Object>>) mappedResponse.get("data");
    }

    public List<GetAnimeData> getAnime() {
        nodeObj.forEach(data->{
            GetAnimeData getAnimeData = new ObjectMapper()
                    .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                    .convertValue(data.get("node"),GetAnimeData.class);
            anime.add(getAnimeData);
        });
        return anime;
    }

    public List<Map<String, Object>> getAnimeMap() {
        nodeObj.forEach(data->{
            Map<String,Object> getAnimeData = new ObjectMapper()
                    .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                    .convertValue(data.get("node"),Map.class);
            animeMap.add(getAnimeData);
        });
        return animeMap;
    }
}
