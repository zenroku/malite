package com.zahir.fathurrahman.malite.app.repository;

import com.zahir.fathurrahman.malite.app.entity.RegisteredField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegisteredFieldRepository extends JpaRepository<RegisteredField,Integer> {
    List<RegisteredField> findByKey(String key);
}
