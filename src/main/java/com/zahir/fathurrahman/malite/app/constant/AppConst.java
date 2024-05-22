package com.zahir.fathurrahman.malite.app.constant;

import java.util.Arrays;
import java.util.List;

public class AppConst {
    public static final String ANIME_SYNC_FIELDS = "start_date,end_date,mean,rank,status,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,start_season,source,rating";
    public static final String LIST_QP_FILTERS_EXAMPLE = "[{\"field\" : \"airingSeason\" , \"op\" : \"=\", \"value\" : \"fall\"},{\"field\" : \"airingYear\" , \"op\" : \"=\", \"value\" : \"2024\"}]";
    public static final String LIST_QP_FILTERS_SORT = "[{\"field\" : \"rank\", \"dir\" : \"asc\"}]";
    public static final String ANIME_ALL_DETAIL_FIELDS = "id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_episodes,start_season,broadcast,source,average_episode_duration,rating,pictures,background,related_anime,related_manga,recommendations,studios,statistics";
    public static final List<String> GET_LIST_ANIME_FIELDS = Arrays.asList("id","title","pict_medium","pict_large","rank","score") ;
    public static final String FILTERS = "filters";
    public static final String SORTS = "sorts";
    public static final String LIMIT = "limit";
    public static final String PAGE = "page";
}
