package com.zahir.fathurrahman.malite.app.service.datasync;

import com.zahir.fathurrahman.malite.core.model.BaseResponse;

public interface AnimeSync {
    BaseResponse syncByRank() throws InterruptedException;
    BaseResponse syncBySeason(int year,String season) throws InterruptedException;
}
