package org.bardframework.commons.captcha;

import lombok.Getter;

@Getter
public class GeneratedCaptcha {
    private final String value;
    private final byte[] image;

    public GeneratedCaptcha(String value, byte[] image) {
        this.value = value;
        this.image = image;
    }

}