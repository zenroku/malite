package com.zahir.fathurrahman.malite.app.model.helper.node;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zahir.fathurrahman.malite.app.model.helper.MALRanking;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MALNodeListData<T> {
    private T node;
    private MALRanking ranking;
}
