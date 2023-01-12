package com.zahir.fathurrahman.malite.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Embeddable
public class BaseResponseArray extends AbstractResponse {
    List<?> data = new ArrayList<>();
}
