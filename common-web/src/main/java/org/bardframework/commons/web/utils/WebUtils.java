package org.bardframework.commons.web.utils;

import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created by Vahid Zafari on 6/10/2016.
 */
public final class WebUtils {

    /**
     * @param response
     * @param data
     * @param contentType
     * @param name
     * @throws IOException
     */
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
}