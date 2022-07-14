package org.bardframework.commons.captcha;

import java.awt.*;

public class CaptchaTypeInfo {
    private final String chars;
    private final Font font;
    private final boolean rtl;

    public CaptchaTypeInfo(String chars, boolean rtl, Font font) {
        this.chars = chars;
        this.rtl = rtl;
        this.font = font;
    }

    public String getChars() {
        return chars;
    }

    public boolean isRtl() {
        return rtl;
    }

    public Font getFont() {
        return font;
    }
}