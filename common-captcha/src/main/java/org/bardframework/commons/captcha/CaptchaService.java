package org.bardframework.commons.captcha;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.bardframework.commons.utils.persian.LetterConverterUtility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class CaptchaService {

    private final Map<CaptchaType, CaptchaLanguageData> localeChars = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public CaptchaService() {
        localeChars.put(CaptchaType.PERSIAN_NUMBER_TEXT, new CaptchaLanguageData("0123456789", false, new Font("Tahoma", Font.PLAIN, 40)));
        localeChars.put(CaptchaType.ENGLISH_NUMBER, new CaptchaLanguageData("0123456789", false, new Font("Tahoma", Font.PLAIN, 40)));
        localeChars.put(CaptchaType.PERSIAN_CHAR, new CaptchaLanguageData("بتجحخدرژسشضظعفقکلمنوهی", true, new Font("Tahoma", Font.PLAIN, 40)));
        localeChars.put(CaptchaType.PERSIAN_NUMBER_CHAR, new CaptchaLanguageData("0123456789بتجحخدرژسشضظعفقکلمنوهی", true, new Font("Tahoma", Font.PLAIN, 40)));
        localeChars.put(CaptchaType.ARABIC_CHAR, new CaptchaLanguageData("ابتثجحخدذرزسشصضطظفقعغکلمنوهی", true, new Font("Tahoma", Font.PLAIN, 40)));
        localeChars.put(CaptchaType.ARABIC_NUMBER_CHAR, new CaptchaLanguageData("0123456789ابتثجحخدذرزسشصضطظفقعغکلمنوهی", true, new Font("Tahoma", Font.PLAIN, 40)));
        localeChars.put(CaptchaType.ENGLISH_CHAR, new CaptchaLanguageData("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", false, new Font("Tahoma", Font.PLAIN, 40)));
        localeChars.put(CaptchaType.ENGLISH_NUMBER_CHAR, new CaptchaLanguageData("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", false, new Font("Tahoma", Font.PLAIN, 40)));
    }

    public Captcha generateCaptcha(CaptchaType captchaType, int maxChars) throws IOException {
        CaptchaLanguageData captchaLanguageData = this.getCaptchaData(captchaType);
        if (null == captchaLanguageData) {
            throw new IllegalStateException(String.format("no chars specify for type %s to generate captcha", captchaType));
        }
        StringBuilder captchaText;
        if (StringUtils.isNumeric(captchaLanguageData.getChars())) {
            captchaText = new StringBuilder(RandomStringUtils.random(maxChars, captchaLanguageData.getChars()));
        } else {
            captchaText = this.removeContinuousNumbers(RandomStringUtils.random(maxChars, captchaLanguageData.getChars()), captchaLanguageData.getChars());
        }
        String text = captchaText.toString();
        String textForImage;
        if (captchaType == CaptchaType.PERSIAN_NUMBER_TEXT) {
            textForImage = LetterConverterUtility.convertDigitToFarsiLetter(Long.valueOf(text));
        } else {
            textForImage = captchaLanguageData.isRtl() ? captchaText.reverse().toString() : captchaText.toString();
        }
        return new Captcha(text, this.generateImage(captchaType, textForImage, this.getFont(captchaType, captchaLanguageData)));
    }

    /**
     * Generates a PNG image of text 180 pixels wide, 40 pixels high with white background.
     *
     * @param text expects string size eight (8) characters.
     * @return byte array that is a PNG image generated with text displayed.
     */
    private byte[] generateImage(CaptchaType type, String text, Font font) throws IOException {
        int height = 40;
        int width = height * text.length();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics.setColor(new Color(RandomUtils.nextInt(170, 255), RandomUtils.nextInt(180, 255), RandomUtils.nextInt(200, 255)));
        graphics.fillRect(0, 0, width, height);

        graphics.setFont(font);
        graphics.setColor(this.getRandomColor());
        int start = 10;
        if (type != CaptchaType.PERSIAN_NUMBER_TEXT) {
            for (int i = 0; i < text.length(); i++) {
                graphics.drawString(text.substring(i, i + 1), start + (i * 20), random.nextInt(15) + 25);
            }
        } else {
            String[] parts = text.split(StringUtils.SPACE);
            for (int i = 0; i < parts.length; i++) {
                graphics.drawString(parts[i], start + (i * 20), random.nextInt(15) + 25);
            }
        }
        for (int i = 0; i < random.nextInt(20); i++) {
            graphics.drawOval(random.nextInt(height), random.nextInt(height), random.nextInt(500), random.nextInt(100));
        }
        graphics.dispose();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", outputStream);
            return outputStream.toByteArray();
        }
    }

    /**
     * remove numbers from first, last amd continuous
     *
     */
    private StringBuilder removeContinuousNumbers(String text, String validChars) {
        StringBuilder result = new StringBuilder();
        boolean previousNumber = false;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i)) && (i == 0 || i == text.length() - 1 || previousNumber)) {
                String notDigit = RandomStringUtils.random(1, validChars);
                while (Character.isDigit(notDigit.charAt(0))) {
                    notDigit = RandomStringUtils.random(1, validChars);
                }
                result.append(notDigit);
                previousNumber = false;
            } else {
                result.append(text.charAt(i));
                previousNumber = Character.isDigit(text.charAt(i));
            }
        }
        return result;
    }

    private Color getRandomColor() {
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    protected CaptchaLanguageData getCaptchaData(CaptchaType type) {
        return localeChars.get(type);
    }

    protected Font getFont(CaptchaType type, CaptchaLanguageData data) {
        return data.getFont();
    }

    public enum CaptchaType {
        ENGLISH_CHAR, ENGLISH_NUMBER_CHAR, ENGLISH_NUMBER, PERSIAN_CHAR, PERSIAN_NUMBER_CHAR, PERSIAN_NUMBER_TEXT, ARABIC_CHAR, ARABIC_NUMBER_CHAR
    }

    public static class Captcha {
        private final String text;
        private final byte[] image;

        public Captcha(String text, byte[] image) {
            this.text = text;
            this.image = image;
        }

        public String getText() {
            return text;
        }

        public byte[] getImage() {
            return image;
        }
    }

    protected static class CaptchaLanguageData {
        private final String chars;
        private final Font font;
        private final boolean rtl;

        public CaptchaLanguageData(String chars, boolean rtl, Font font) {
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
}
