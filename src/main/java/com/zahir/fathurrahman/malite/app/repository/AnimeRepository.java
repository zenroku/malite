package com.zahir.fathurrahman.malite.app.repository;

import com.zahir.fathurrahman.malite.app.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime,Long> {
}
