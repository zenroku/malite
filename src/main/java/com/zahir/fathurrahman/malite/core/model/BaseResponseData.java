package com.zahir.fathurrahman.malite.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Setter
@Getter
@Embeddable
public class BaseResponseData extends BaseResponse {
    private Object data;
}
