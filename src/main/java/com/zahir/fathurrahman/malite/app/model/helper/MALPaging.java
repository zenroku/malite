package com.zahir.fathurrahman.malite.app.model.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties
public class MALPaging {
    private String next;
    private String previous;
}
