package com.zahir.fathurrahman.malite.app.service.datasync.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zahir.fathurrahman.malite.app.constant.AppConst;
import com.zahir.fathurrahman.malite.app.constant.Modules;
import com.zahir.fathurrahman.malite.app.entity.Anime;
import com.zahir.fathurrahman.malite.app.model.helper.MALHTTPResponse;
import com.zahir.fathurrahman.malite.app.model.helper.QueryParameter;
import com.zahir.fathurrahman.malite.app.model.helper.node.MALNodeList;
import com.zahir.fathurrahman.malite.app.model.helper.node.MALNodeListData;
import com.zahir.fathurrahman.malite.app.repository.AnimeRepository;
import com.zahir.fathurrahman.malite.app.service.datasync.AnimeSync;
import com.zahir.fathurrahman.malite.app.service.helper.MALRequestService;
import com.zahir.fathurrahman.malite.core.model.BaseResponse;
import com.zahir.fathurrahman.malite.core.util.MALDataConverter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AnimeSyncImpl implements AnimeSync {
    private MALRequestService malRequestService;
    private AnimeRepository animeRepository;
    @Override
    public BaseResponse syncByRank() {
        BaseResponse rsp = new BaseResponse();
        log.info("SYNCING ANIME BY RANKING TO MAL");

        QueryParameter qp = new QueryParameter();
        qp.setRankingType("tv");
        qp.setFields(AppConst.ANIME_SYNC_FIELDS);
        boolean hasNext = true;
        int fetchProgress = 0;
        int fetchSize = 100;
        while (hasNext) {
            qp.setLimit(fetchSize);
            qp.setOffset(fetchProgress);
            MALHTTPResponse malResponse = malRequestService.get("/anime/ranking", qp, Modules.GET_ANIME_RANKING);
            if (malResponse.getResponseCode() == 200) {
                MALNodeList<Map<String,Object>> response = null;
                try {
                    response = new ObjectMapper().readValue(malResponse.getData(),new TypeReference<>(){});
                } catch (Exception e) {
                    log.info("invalid response data of {}",malResponse.getData());
                }

                if (response != null) {
                    List<Anime> animes = new ArrayList<>();
                    for (MALNodeListData<Map<String,Object>> node : response.getData()) {
                        Map<String,Object> n = node.getNode();
                        try {
                            Long id = MALDataConverter.lg(n.get("id"));
                            log.info("syncing anime with id {}",id);
                            Optional<Anime> animeOptional = animeRepository.findById(id);
                            Anime anime;
                            if (animeOptional.isPresent()) {
                                anime = animeOptional.get();
                            } else {
                                anime = new Anime();
                                anime.setId(id);
                            }
                            animeGlobalMapper(n,anime);
                            animes.add(anime);
                        } catch (Exception e) {
                            log.info("unexpected result from mal of anime id {}",n.get("id"));
                            log.info("{}",e);
                        }

                    }
                    animeRepository.saveAll(animes);
                    if (response.getPaging().getNext() == null) hasNext = false;
                    fetchProgress += fetchSize;
                }
            } else {
                hasNext = false;
            }
        }


        return rsp;
    }

    @Override
    public BaseResponse syncBySeason(int year,String season) {
        BaseResponse rsp = new BaseResponse();
        log.info("SYNCING ANIME BY RANKING TO MAL");

        QueryParameter qp = new QueryParameter();
        qp.setFields(AppConst.ANIME_SYNC_FIELDS);
        boolean hasNext = true;
        int fetchProgress = 0;
        int fetchSize = 100;
        while (hasNext) {
            qp.setLimit(fetchSize);
            qp.setOffset(fetchProgress);
            MALHTTPResponse malResponse = malRequestService.get(String.format("/anime/season/%d/%s",year,season), qp, Modules.GET_SEASONAL_ANIME);
            if (malResponse.getResponseCode() == 200) {
                MALNodeList<Map<String,Object>> response = null;
                try {
                    response = new ObjectMapper().readValue(malResponse.getData(),new TypeReference<>(){});
                } catch (Exception e) {
                    log.info("invalid response data of {}",malResponse.getData());
                }

                if (response != null) {
                    int dataSize = response.getData().size();
                    log.info("total fetched data : {}",dataSize);
                    List<Anime> animes = new ArrayList<>();
                    int row = 1;
                    for (MALNodeListData<Map<String,Object>> node : response.getData()) {
                        Map<String,Object> n = node.getNode();
                        try {
                            Long id = MALDataConverter.lg(n.get("id"));
                            log.info("syncing row {} anime with id {}",row,id);
                            Optional<Anime> animeOptional = animeRepository.findById(id);
                            Anime anime;
                            if (animeOptional.isPresent()) {
                                anime = animeOptional.get();
                            } else {
                                anime = new Anime();
                                anime.setId(id);
                            }
                            animeGlobalMapper(n,anime);
                            animes.add(anime);
                        } catch (Exception e) {
                            log.info("unexpected result from mal of anime id {}",n.get("id"));
                            log.info("{}",e);
                        }
                        row++;
                    }
                    animeRepository.saveAll(animes);
                    if (response.getPaging().getNext() != null) {
                        log.info("pagination next result {}",response.getPaging().getNext());
                    } else {
                        hasNext = false;
                    }
                    log.info("done mapping progress of offset {}-{}",fetchProgress,(fetchProgress+dataSize));
                    fetchProgress += fetchSize;
                }
            } else {
                hasNext = false;
            }
        }

        return rsp;
    }

    private void animeGlobalMapper(Map<String,Object> data, Anime anime){
        anime.setTitle(String.valueOf(data.get("title")));
        Map<String,Object> pict = (Map<String,Object>) data.get("main_picture");
        anime.setPictMedium(String.valueOf(pict.get("medium")));
        anime.setPictLarge(String.valueOf(pict.get("large")));
        anime.setStartDate(MALDataConverter.ld(data.get("start_date")));
        anime.setEndDate(MALDataConverter.ld(data.get("end_date")));
        anime.setScore(MALDataConverter.dbl(data.get("mean")));
        anime.setRank(MALDataConverter.itg(data.get("rank")));
        anime.setUsers(MALDataConverter.itg(data.get("num_list_users")));
        anime.setScoringUsers(MALDataConverter.itg(data.get("num_scoring_users")));
        anime.setNsfw(MALDataConverter.str(data.get("nsfw")));
        anime.setStatus(MALDataConverter.str(data.get("status")));
        anime.setCreatedAt(MALDataConverter.ldt(data.get("created_at")));
        anime.setUpdatedAt(MALDataConverter.ldt(data.get("updated_at")));
        anime.setMediaType(MALDataConverter.str(data.get("media_type")));
        Map<String,Object> season = (Map<String,Object>) data.get("start_season");
        anime.setAiringYear(MALDataConverter.itg(season.get("year")));
        anime.setAiringSeason(MALDataConverter.str(season.get("season")));
        anime.setSource(MALDataConverter.str(data.get("source")));
        anime.setRating(MALDataConverter.str(data.get("rating")));
        anime.setSyncedAt(LocalDateTime.now());
    }
}
