package com.zahir.fathurrahman.malite.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse implements Serializable {
    private Boolean success = true;
    private Integer status = 200;
    private String message;
}
