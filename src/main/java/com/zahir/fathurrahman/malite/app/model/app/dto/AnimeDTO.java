package com.zahir.fathurrahman.malite.app.model.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimeDTO {
    private Long id;
    private String title;
    private String pictMedium;
    private String pictLarge;
    private Integer rank;
    private Double score;
}
