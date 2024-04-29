package com.zahir.fathurrahman.malite.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryParameter {
    private String q;
    private Integer limit;
    private Integer offset;
    private String fields;
    @JsonProperty("ranking_type")
    private String rankingType;
    private String sort;
}
