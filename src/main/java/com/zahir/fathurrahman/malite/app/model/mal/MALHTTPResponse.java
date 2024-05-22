package com.zahir.fathurrahman.malite.app.model.mal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties
public class MALHTTPResponse {
    private String message = "Failed";
    private int responseCode = 500;
    private String data;
}
