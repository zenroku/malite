package com.zahir.fathurrahman.malite.app.entity;

import com.zahir.fathurrahman.malite.app.constant.RegisteredType;
import com.zahir.fathurrahman.malite.app.constant.Tables;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = Tables.REGISTERED_FIELDS)
public class RegisteredField {
    @Id
    private Integer id;
    private String key;
    private String fieldName;
    private String columnName;
    @Enumerated(EnumType.STRING)
    private RegisteredType dataType;
    private boolean isActive;
}
