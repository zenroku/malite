package com.zahir.fathurrahman.malite.app.model.anime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zahir.fathurrahman.malite.app.model.MALMainPicture;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MALAnime {
    private Long id;
    private String title;
    @JsonProperty("main_picture")
    private MALMainPicture mainPicture;
}
