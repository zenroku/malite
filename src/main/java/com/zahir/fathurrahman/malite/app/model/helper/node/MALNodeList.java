package com.zahir.fathurrahman.malite.app.model.helper.node;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zahir.fathurrahman.malite.app.model.helper.MALPaging;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MALNodeList<T> {
    private List<MALNodeListData<T>> data;
    private MALPaging paging;
}
