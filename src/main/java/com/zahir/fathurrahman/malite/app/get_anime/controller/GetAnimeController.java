package com.zahir.fathurrahman.malite.app.get_anime.controller;

import com.zahir.fathurrahman.malite.core.model.BaseResponseArray;

import javax.servlet.http.HttpServletRequest;

public interface GetAnimeController {
    BaseResponseArray get(HttpServletRequest request);
}
