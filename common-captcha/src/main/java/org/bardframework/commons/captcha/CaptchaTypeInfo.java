package org.bardframework.commons.captcha;

import lombok.Getter;

import java.awt.*;

@Getter
public class CaptchaTypeInfo {
    private final String chars;
    private final Font font;
    private final boolean rtl;

    public CaptchaTypeInfo(String chars, boolean rtl, Font font) {
        this.chars = chars;
        this.rtl = rtl;
        this.font = font;
    }

}