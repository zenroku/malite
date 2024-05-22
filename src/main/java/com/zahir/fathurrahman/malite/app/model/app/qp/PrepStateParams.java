package com.zahir.fathurrahman.malite.app.model.app.qp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
@Setter
@AllArgsConstructor
public class PrepStateParams {
    private int idx;
    private Object value;

    public void setPreparedStatement(PreparedStatement ps) throws SQLException {
        ps.setObject(this.idx,this.value);
    }
}
