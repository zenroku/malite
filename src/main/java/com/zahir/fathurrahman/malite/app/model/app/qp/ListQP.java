package com.zahir.fathurrahman.malite.app.model.app.qp;

import com.zahir.fathurrahman.malite.app.entity.RegisteredField;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListQP {
    private String table;
    private List<String> fields = new ArrayList<>();
    private List<RegisteredField> regFields = new ArrayList<>();
    private List<QPFilter> filters = new ArrayList<>();
    private List<QPSort> sorts = new ArrayList<>();
    private Integer limit = 10;
    private Integer page = 0;
}
