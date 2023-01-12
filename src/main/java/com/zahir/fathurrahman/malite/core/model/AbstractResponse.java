package com.zahir.fathurrahman.malite.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class AbstractResponse implements Serializable {
    private Boolean success = true;
    private Integer status = 200;
    private String message;
}
