package com.zahir.fathurrahman.malite.app.service.datasync.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zahir.fathurrahman.malite.app.constant.AppConst;
import com.zahir.fathurrahman.malite.app.constant.Modules;
import com.zahir.fathurrahman.malite.app.entity.Anime;
import com.zahir.fathurrahman.malite.app.model.mal.MALHTTPResponse;
import com.zahir.fathurrahman.malite.app.model.mal.MALQueryParameter;
import com.zahir.fathurrahman.malite.app.model.mal.node.MALNodeList;
import com.zahir.fathurrahman.malite.app.model.mal.node.MALNodeListData;
import com.zahir.fathurrahman.malite.app.repository.AnimeRepository;
import com.zahir.fathurrahman.malite.app.service.datasync.AnimeSync;
import com.zahir.fathurrahman.malite.app.service.helper.MALRequestService;
import com.zahir.fathurrahman.malite.core.model.BaseResponse;
import com.zahir.fathurrahman.malite.core.util.MALDataConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public BaseResponse syncByRank() throws InterruptedException {
        log.info("SYNCING ANIME BY RANKING TO MAL");

        MALQueryParameter qp = new MALQueryParameter();
        qp.setRankingType("tv");
        qp.setFields(AppConst.ANIME_SYNC_FIELDS);

        return sync("/anime/ranking",qp,Modules.GET_ANIME_RANKING);
    }

    @Override
    @Transactional
    public BaseResponse syncBySeason(int year,String season) throws InterruptedException {
        log.info("SYNCING ANIME BY SEASON TO MAL");

        MALQueryParameter qp = new MALQueryParameter();
        qp.setFields(AppConst.ANIME_SYNC_FIELDS);

        return sync(String.format("/anime/season/%d/%s",year,season),qp,Modules.GET_SEASONAL_ANIME);
    }
    private BaseResponse sync(String uri, MALQueryParameter qp, Modules module) throws InterruptedException {
        BaseResponse rsp = new BaseResponse();

        boolean hasNext = true;
        int fetchProgress = 0;
        int fetchSize = 200;
        while (hasNext) {
            qp.setLimit(fetchSize);
            qp.setOffset(fetchProgress);
            MALHTTPResponse malResponse = malRequestService.get(uri, qp, module);
            if (malResponse.getResponseCode() == 200) {
                MALNodeList<Map<String,Object>> response = null;
                try {
                    response = new ObjectMapper().readValue(malResponse.getData(),new TypeReference<>(){});
                } catch (Exception e) {
                    log.info("invalid response data of {}",malResponse.getData());
                }

                if (response != null) {
                    List<Anime> animes = new ArrayList<>();
                    int dataSize = response.getData().size();
                    log.info("fetched with total data of {}",dataSize);
                    for (MALNodeListData<Map<String,Object>> node : response.getData()) {
                        Map<String,Object> n = node.getNode();
                        try {
                            Long id = MALDataConverter.lg(n.get("id"));
                            Optional<Anime> animeOptional = animeRepository.findById(id);
                            Anime anime;
                            if (animeOptional.isPresent()) {
                                log.info("anime with id {}, and title : {}; found, updating...",id,n.get("title"));
                                anime = animeOptional.get();
                            } else {
                                log.info("anime with id {}, and title : {}; not found, creating...",id,n.get("title"));
                                anime = new Anime();
                                anime.setId(id);
                            }
                            animeGlobalMapper(n,anime);
                            anime.setRank(node.getRanking().getRank());
                            animes.add(anime);
                        } catch (Exception e) {
                            log.info("failed to save... unexpected result from mal of anime id {}",n.get("id"));
                            log.info("{}",e);
                        }
                    }

                    log.info("done mapping progress of offset {}-{}",fetchProgress,(fetchProgress+dataSize));
                    animeRepository.saveAll(animes);
                    if (response.getPaging().getNext() == null) {
                        int lastIndex = fetchProgress+dataSize;
                        String messageResult = String.format("total synced anime is : %d",lastIndex);
                        rsp.setMessage(messageResult);
                        log.info(messageResult);
                        hasNext = false;
                    }
                    fetchProgress += fetchSize;
                }
            } else {
                hasNext = false;
            }
            Thread.sleep(5000L);
        }


        return rsp;
    }

    private void animeGlobalMapper(Map<String,Object> data, Anime anime){
        anime.setTitle(String.valueOf(data.get("title")));
        Map<String,Object> pict = (Map<String,Object>) data.get("main_picture");
        if (pict != null) {
            anime.setPictMedium(String.valueOf(pict.get("medium")));
            anime.setPictLarge(String.valueOf(pict.get("large")));
        } else {
            log.info("main_picture is null, maybe its new anime or hasn't data of it");
        }
        anime.setStartDate(MALDataConverter.ld(data.get("start_date")));
        anime.setEndDate(MALDataConverter.ld(data.get("end_date")));
        anime.setScore(MALDataConverter.dbl(data.get("mean")));
        anime.setUsers(MALDataConverter.itg(data.get("num_list_users")));
        anime.setScoringUsers(MALDataConverter.itg(data.get("num_scoring_users")));
        anime.setNsfw(MALDataConverter.str(data.get("nsfw")));
        anime.setStatus(MALDataConverter.str(data.get("status")));
        anime.setCreatedAt(MALDataConverter.ldt(data.get("created_at")));
        anime.setUpdatedAt(MALDataConverter.ldt(data.get("updated_at")));
        anime.setMediaType(MALDataConverter.str(data.get("media_type")));
        Map<String,Object> season = (Map<String,Object>) data.get("start_season");
        if (season != null) {
            anime.setAiringYear(MALDataConverter.itg(season.get("year")));
            anime.setAiringSeason(MALDataConverter.str(season.get("season")));
        } else {
            log.info("season is null, maybe its new anime or hasn't data of it");
        }
        anime.setSource(MALDataConverter.str(data.get("source")));
        anime.setRating(MALDataConverter.str(data.get("rating")));
        anime.setSyncedAt(LocalDateTime.now());
    }
}
