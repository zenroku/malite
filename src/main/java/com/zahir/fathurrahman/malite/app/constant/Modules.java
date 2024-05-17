package com.zahir.fathurrahman.malite.app.constant;

import java.util.List;

public enum Modules {
    GET_ANIME(ModuleParamConfig.GET_ANIME),
    GET_ANIME_DETAILS(ModuleParamConfig.GET_ANIME_DETAILS),
    GET_ANIME_RANKING(ModuleParamConfig.GET_ANIME_RANKINGS),
    GET_SEASONAL_ANIME(ModuleParamConfig.GET_SEASONAL_ANIME);

    private final List<String> availParams;

    Modules(List<String> availParams){
        this.availParams = availParams;
    }

    public List<String> getAvailParams(){
        return this.availParams;
    }
}
