package org.bardframework.commons.waf.extractor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RequestKeyDetector {

    String getUniqueKey(HttpServletRequest request, HttpServletResponse response);

}
