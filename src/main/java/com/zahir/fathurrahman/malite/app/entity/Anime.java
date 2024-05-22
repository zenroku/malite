package com.zahir.fathurrahman.malite.app.entity;

import com.zahir.fathurrahman.malite.app.constant.Tables;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = Tables.ANIME)
public class Anime {
    @Id
    private Long id;
    private String title;
    private String pictMedium;
    private String pictLarge;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double score;
    private Integer rank;
    private String status;
    private Integer users;
    private Integer scoringUsers;
    private String nsfw;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String mediaType;
    private Integer airingYear;
    private String airingSeason;
    private String source;
    private String rating;
    private LocalDateTime syncedAt;
}
