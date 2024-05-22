package com.zahir.fathurrahman.malite.app.model.app.qp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.PreparedStatementSetter;

@Getter
@Setter
public class QPStatement {
    private String query;
    private PreparedStatementSetter statement;
}
