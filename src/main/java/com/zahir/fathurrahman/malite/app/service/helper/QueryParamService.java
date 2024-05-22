package com.zahir.fathurrahman.malite.app.service.helper;

import com.zahir.fathurrahman.malite.app.model.app.qp.ListQP;
import com.zahir.fathurrahman.malite.app.model.app.qp.QPStatement;

public interface QueryParamService {
    QPStatement queryBuilder(ListQP qp);
}
