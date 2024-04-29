package com.zahir.fathurrahman.malite.app.service.anime;
import com.zahir.fathurrahman.malite.core.model.BaseResponseArray;

import javax.servlet.http.HttpServletRequest;

public interface GetAnimeService {
    BaseResponseArray getAnime(HttpServletRequest request);
}
