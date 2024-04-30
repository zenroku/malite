package com.zahir.fathurrahman.malite.app.controller.anime.impl;

import com.zahir.fathurrahman.malite.app.constant.Routes;
import com.zahir.fathurrahman.malite.app.controller.anime.AnimeController;
import com.zahir.fathurrahman.malite.app.service.anime.AnimeService;
import com.zahir.fathurrahman.malite.core.model.BaseResponseData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(Routes.ANIME)
@AllArgsConstructor
public class AnimeControllerImpl implements AnimeController {
    private AnimeService animeService;

    @Override
    @GetMapping
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
    @GetMapping("/seasonal")
    public BaseResponseData getSeasonalAnime(HttpServletRequest request) {
        return animeService.getSeasonalAnime(request);
    }
}
