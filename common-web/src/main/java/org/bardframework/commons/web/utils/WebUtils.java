package org.bardframework.commons.web.utils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created by Vahid Zafari on 6/10/2016.
 */
@UtilityClass
public class WebUtils {

    private static final char RLO = '\u202E';
    private static final char EMPTY = '\u0000';
    private static final char UNDERLINE = '_';
    private static final char SPACE = ' ';

    public static void fillResponse(HttpServletResponse response, byte[] data, String contentType, String name)
            throws IOException {
        response.setContentType(contentType);
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());

        String fileName = URLEncoder.encode(name, StandardCharsets.UTF_8).replace("+", "%20");
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
     * remove &gt; &lt; RLO from input string, escapeHtml4
     *
     * @return cleaned string
     */
    public static String escapeString(String value) {
        if (null == value) {
            return null;
        }
        return StringEscapeUtils.escapeHtml4(value)
                .replace(RLO, EMPTY)
                .replace('\n', SPACE)
                .replace('\r', SPACE)
                .replace('\t', SPACE)
                .replace('"', UNDERLINE)
                .replace('\'', UNDERLINE)
                .replace('<', UNDERLINE)
                .replace('>', UNDERLINE);
    }
}
