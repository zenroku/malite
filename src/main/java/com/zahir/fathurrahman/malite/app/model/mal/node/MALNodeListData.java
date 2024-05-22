package com.zahir.fathurrahman.malite.app.model.mal.node;

import com.zahir.fathurrahman.malite.app.model.mal.MALRanking;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MALNodeListData<T> {
    private T node;
    private MALRanking ranking;
}
