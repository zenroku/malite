package com.zahir.fathurrahman.malite.app.service.datasync;

import com.zahir.fathurrahman.malite.core.model.BaseResponse;

public interface AnimeSync {
    BaseResponse syncByRank();
    BaseResponse syncBySeason(int year,String season);
}
