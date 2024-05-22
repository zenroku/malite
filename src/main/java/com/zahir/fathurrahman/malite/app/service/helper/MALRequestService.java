package com.zahir.fathurrahman.malite.app.service.helper;

import com.zahir.fathurrahman.malite.app.constant.Modules;
import com.zahir.fathurrahman.malite.app.model.mal.MALHTTPResponse;
import com.zahir.fathurrahman.malite.app.model.mal.MALQueryParameter;

public interface MALRequestService {
    MALHTTPResponse get(String uri, MALQueryParameter qp, Modules module);
}
