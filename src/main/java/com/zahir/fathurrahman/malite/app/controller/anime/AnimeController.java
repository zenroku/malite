package com.zahir.fathurrahman.malite.app.controller.anime;

import com.zahir.fathurrahman.malite.core.model.BaseResponseData;

import javax.servlet.http.HttpServletRequest;

public interface AnimeController {
    BaseResponseData get(HttpServletRequest request);
    BaseResponseData getAnimeDetail(HttpServletRequest request);
    BaseResponseData getAnimeRanking(HttpServletRequest request);
    BaseResponseData getSeasonalAnime(HttpServletRequest request);
}
