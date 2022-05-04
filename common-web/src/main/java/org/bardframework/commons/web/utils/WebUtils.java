package org.bardframework.commons.web.utils;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created by Vahid Zafari on 6/10/2016.
 */
public final class WebUtils {

    private static final char RLO = '\u202E';
    private static final char EMPTY = '\u0000';

    public static void fillResponse(HttpServletResponse response, byte[] data, String contentType, String name)
            throws IOException {
        response.setContentType(contentType);
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());

        String fileName = URLEncoder.encode(name, StandardCharsets.UTF_8.name()).replace("+", "%20");
        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
//        response.setHeader("Content-Disposition", String.format("inline; filename*=UTF-8''%s", fileName));

        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        response.setHeader("Content-Disposition", String.format("attachment; filename*=UTF-8''%s", fileName));
        response.setContentLength(data.length);
        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(data, response.getOutputStream());
    }

    /**
     * remove < > RLO from input string, escapeHtml4, escapeJava, escapeXSI
     *
     * @return cleaned string
     */
    public static String escapeString(String value) {
        if (null == value) {
            return null;
        }
        value = StringEscapeUtils.escapeHtml4(value);
        value = StringEscapeUtils.escapeJava(value);
        value = StringEscapeUtils.escapeXSI(value);
        return value.replace(RLO, EMPTY)
                .replace('<', EMPTY)
                .replace('>', EMPTY);
    }
}