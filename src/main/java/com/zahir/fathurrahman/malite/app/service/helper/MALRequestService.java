package com.zahir.fathurrahman.malite.app.service.helper;

import com.zahir.fathurrahman.malite.app.constant.Modules;
import com.zahir.fathurrahman.malite.app.model.helper.MALHTTPResponse;
import com.zahir.fathurrahman.malite.app.model.helper.QueryParameter;

public interface MALRequestService {
    MALHTTPResponse get(String uri, QueryParameter qp, Modules module);
}
