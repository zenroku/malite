package com.zahir.fathurrahman.malite.app.model.helper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MALHTTPResponse {
    private String message = "Failed";
    private int responseCode = 500;
    private String data;
}
