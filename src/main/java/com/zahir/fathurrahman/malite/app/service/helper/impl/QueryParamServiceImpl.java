package com.zahir.fathurrahman.malite.app.service.helper.impl;

import com.zahir.fathurrahman.malite.app.constant.RegisteredType;
import com.zahir.fathurrahman.malite.app.entity.RegisteredField;
import com.zahir.fathurrahman.malite.app.model.app.qp.*;
import com.zahir.fathurrahman.malite.app.service.helper.QueryParamService;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueryParamServiceImpl implements QueryParamService {

    public QPStatement queryBuilder(ListQP qp){
        QPStatement result = new QPStatement();
        StringBuilder qb = new StringBuilder();
        List<PrepStateParams> ps = new ArrayList<>();
        int pidx = 0;

        qb.append("SELECT ");
        qb.append(String.join(",",qp.getFields()));
        qb.append(" FROM ");
        qb.append(qp.getTable());
        if (!qp.getFilters().isEmpty()) {

            List<String> filter = new ArrayList<>();
            for (QPFilter f : qp.getFilters()) {
                RegisteredField rf = getRegField(f.getField(),qp);
                if (rf != null) {
                    pidx++;
                    filter.add(String.format("\"%s\" %s ?",rf.getColumnName(),f.getOp()));
                    ps.add(new PrepStateParams(pidx,setFilterValue(rf.getDataType(),f.getValue())));
                }
            }
            if (!filter.isEmpty()) {
                qb.append(" WHERE ");
                qb.append(String.join(" AND ",filter));
            }
        }

        qb.append(" ORDER BY ");
        if (!qp.getSorts().isEmpty()) {
            List<String> sort = new ArrayList<>();
            for (QPSort s : qp.getSorts()) {
                sort.add(s.getField() + " " + s.getDir());
            }
            qb.append(String.join(",",sort));
        } else {
            qb.append("id asc");
        }

        qb.append(" LIMIT ? OFFSET ?");
        pidx++;
        ps.add(new PrepStateParams(pidx,qp.getLimit()));
        pidx++;
        ps.add(new PrepStateParams(pidx,(qp.getPage() * qp.getLimit())));

        PreparedStatementSetter pss = preparedStatement -> {
            for (PrepStateParams stateParams : ps) {
                stateParams.setPreparedStatement(preparedStatement);
            }
        };

        result.setQuery(qb.toString());
        result.setStatement(pss);

        return result;
    }

    private RegisteredField getRegField(String fieldName,ListQP qp){
        List<RegisteredField> filter = qp.getRegFields().stream().filter(rf -> rf.getFieldName().equals(fieldName)).collect(Collectors.toList());
        return filter.size() > 0 ? filter.get(0) : null;
    }

    private Object setFilterValue(RegisteredType type,String str) {
        Object retVal = null;
        switch (type) {
            case STRING:
                retVal = str;
                break;
            case INTEGER:
                retVal = Integer.parseInt(str);
                break;
            default:
                break;
        }
        return retVal;
    }
}
