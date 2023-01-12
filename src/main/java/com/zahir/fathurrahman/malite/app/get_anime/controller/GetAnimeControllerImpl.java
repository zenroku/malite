package com.zahir.fathurrahman.malite.app.get_anime.controller;

import com.zahir.fathurrahman.malite.app.get_anime.service.GetAnimeService;
import com.zahir.fathurrahman.malite.core.model.BaseResponseArray;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/get_anime")
@AllArgsConstructor
public class GetAnimeControllerImpl implements GetAnimeController{
    private GetAnimeService getAnimeService;

    @Override
    @GetMapping
    public BaseResponseArray get(HttpServletRequest request) {
        return getAnimeService.getAnime(request);
    }
}
