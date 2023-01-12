package com.zahir.fathurrahman.malite.app.get_anime.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class GetAnimeData {

    private Integer id;
    private String title;
    private Map<String,Object> mainPicture = new HashMap<>();

    @Override
    public String toString() {
        return "GetAnimeData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", mainPicture=" + mainPicture +
                '}';
    }
}
