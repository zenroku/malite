package com.zahir.fathurrahman.malite.app.service.anime;
import com.zahir.fathurrahman.malite.core.model.BaseResponseData;

import javax.servlet.http.HttpServletRequest;

public interface AnimeService {
    BaseResponseData searchAnime(HttpServletRequest request);
    BaseResponseData getAnimeDetail(HttpServletRequest request);
    BaseResponseData getAnimeList(HttpServletRequest request);
}
