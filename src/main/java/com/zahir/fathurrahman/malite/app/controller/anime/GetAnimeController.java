package com.zahir.fathurrahman.malite.app.controller.anime;

import com.zahir.fathurrahman.malite.core.model.BaseResponseArray;

import javax.servlet.http.HttpServletRequest;

public interface GetAnimeController {
    BaseResponseArray get(HttpServletRequest request);
}
