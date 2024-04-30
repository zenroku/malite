package com.zahir.fathurrahman.malite.app.constant;

import java.util.List;

public class ModuleParamConfig {
    public static final List<String> GET_ANIME = List.of("q","limit","offset","fields");
    public static final List<String> GET_ANIME_DETAILS = List.of("fields");
    public static final List<String> GET_ANIME_RANKINGS = List.of("rankingType","limit","offset","fields");
    public static final List<String> GET_SEASONAL_ANIME = List.of("sort","limit","offset","fields");
}
