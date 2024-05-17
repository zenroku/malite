package com.zahir.fathurrahman.malite.app.controller.anime.impl;

import com.zahir.fathurrahman.malite.app.constant.Routes;
import com.zahir.fathurrahman.malite.app.controller.anime.AnimeController;
import com.zahir.fathurrahman.malite.app.service.anime.AnimeService;
import com.zahir.fathurrahman.malite.app.service.datasync.AnimeSync;
import com.zahir.fathurrahman.malite.core.model.BaseResponse;
import com.zahir.fathurrahman.malite.core.model.BaseResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(Routes.ANIME)
@AllArgsConstructor
@Api(hidden = true, tags = "ASD")
public class AnimeControllerImpl implements AnimeController {
    private AnimeService animeService;
    private AnimeSync animeSync;

    @Override
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "q")
    })
    public BaseResponseData get(HttpServletRequest request) {
        return animeService.getAnime(request);
    }

    @Override
    @GetMapping("/detail")
    public BaseResponseData getAnimeDetail(HttpServletRequest request) {
        return animeService.getAnimeDetail(request);
    }

    @Override
    @GetMapping("/ranking")
    public BaseResponseData getAnimeRanking(HttpServletRequest request) {
        return animeService.getAnimeRanking(request);
    }

    @Override
    @PostMapping("/sync_db_anime")
    public BaseResponse syncDbAnime() {
        return animeSync.syncBySeason(2024,"winter");
    }
}
