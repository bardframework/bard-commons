package org.bardframework.commons.captcha;

public class GeneratedCaptcha {
    private final String value;
    private final byte[] image;

    public GeneratedCaptcha(String value, byte[] image) {
        this.value = value;
        this.image = image;
    }

    public String getValue() {
        return value;
    }

    public byte[] getImage() {
        return image;
    }
}