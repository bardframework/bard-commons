package org.bardframework.commons.waf.extractor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestKeyDetector {

    String getUniqueKey(HttpServletRequest request, HttpServletResponse response);

}
