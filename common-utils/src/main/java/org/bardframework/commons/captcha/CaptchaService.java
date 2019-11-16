package org.bardframework.commons.captcha;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class CaptchaService {

    private final Map<String, CaptchaLanguageData> localeChars = new ConcurrentHashMap<>();
    private Random random = new Random();

    public CaptchaService() {
        localeChars.put("fa", new CaptchaLanguageData("0123456789بتجحخدرژسشضظعفقکلمنوهی", true, new Font("Tahoma", Font.PLAIN, 40)));
        localeChars.put("ar", new CaptchaLanguageData("0123456789ابتثجحخدذرزسشصضطظفقعغکلمنوهی", true, new Font("Tahoma", Font.PLAIN, 40)));
        localeChars.put("en", new CaptchaLanguageData("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", false, new Font("Tahoma", Font.PLAIN, 40)));
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            String random = RandomStringUtils.random(10, "3234545657676765");
            System.out.println(random + " -> " + new CaptchaService().removeContinuousNumbers(random, "0123456789آابپتثجچحخدذرزژسشصضطظعغفقکگلمنوهی"));
        }
    }

    public Captcha generateCaptcha(String locale, int maxChars) throws IOException {
        CaptchaLanguageData captchaLanguageData = this.getCaptchaData(locale);
        if (null == captchaLanguageData) {
            throw new IllegalStateException(String.format("no chars specify for locale %s to generate captcha", locale));
        }
        StringBuilder captchaText = this.removeContinuousNumbers(RandomStringUtils.random(maxChars, captchaLanguageData.getChars()), captchaLanguageData.getChars());
        String text = captchaText.toString();
        String textForImage = captchaLanguageData.isRtl() ? captchaText.reverse().toString() : captchaText.toString();
        return new Captcha(text, this.generateImage(textForImage, this.getFont(locale, captchaLanguageData)));
    }

    /**
     * Generates a PNG image of text 180 pixels wide, 40 pixels high with white background.
     *
     * @param text expects string size eight (8) characters.
     * @return byte array that is a PNG image generated with text displayed.
     */
    private byte[] generateImage(String text, Font font) throws IOException {
        text = text.replaceAll(" ", "");
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
        for (int i = 0; i < text.length(); i++) {
            graphics.drawString(text.substring(i, i + 1), start + (i * 20), random.nextInt(15) + 25);
        }
        for (int i = 0; i < random.nextInt(text.length()); i++) {
            graphics.setColor(this.getRandomColor());
            graphics.drawOval(random.nextInt(height), random.nextInt(height), random.nextInt(1000), random.nextInt(1000));
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
     * @param text
     * @param validChars
     * @return
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

    protected CaptchaLanguageData getCaptchaData(String locale) {
        return localeChars.get(locale);
    }

    protected Font getFont(String locale, CaptchaLanguageData data) {
        return data.getFont();
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
