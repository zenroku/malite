package com.zahir.fathurrahman.malite.app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MALNodeList<T> {
    private List<MALNodeListData<T>> data;
    private MALPaging paging;
}