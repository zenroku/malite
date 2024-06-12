package com.zahir.fathurrahman.malite.app.controller.anime.impl;

import com.zahir.fathurrahman.malite.app.constant.AppConst;
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
            @ApiImplicitParam(paramType = "query",name = "q",dataTypeClass = String.class)
    })
    public BaseResponseData get(HttpServletRequest request) {
        return animeService.searchAnime(request);
    }

    @Override
    @GetMapping("/detail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "id", value = "56885", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query",name = "fields", value = AppConst.ANIME_ALL_DETAIL_FIELDS, dataTypeClass = String.class)
    })
    public BaseResponseData getAnimeDetail(HttpServletRequest request) {
        return animeService.getAnimeDetail(request);
    }

    @Override
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "filters",value = AppConst.LIST_QP_FILTERS_EXAMPLE, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query",name = "sorts", value = AppConst.LIST_QP_FILTERS_SORT, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query",name = "limit", value = "10", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query",name = "page", value = "0", dataTypeClass = String.class)
    })
    public BaseResponseData getAnimeList(HttpServletRequest request) {
        return animeService.getAnimeList(request);
    }

    @Override
    @PostMapping("/sync_db_anime")
    public BaseResponse syncDbAnime() throws InterruptedException {
        return animeSync.syncByRank();
    }
}
