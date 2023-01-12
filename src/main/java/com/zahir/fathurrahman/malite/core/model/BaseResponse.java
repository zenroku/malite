package com.zahir.fathurrahman.malite.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Map;

@Getter
@Setter
@Embeddable
public class BaseResponse extends AbstractResponse {
    private Map<String,?> data;
}
