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

    private final Random random = new Random();
    private Map<CaptchaType, CaptchaTypeInfo> captchaTypeInfoMap = new ConcurrentHashMap<>();

    public CaptchaService() {
        captchaTypeInfoMap.put(CaptchaType.PERSIAN_NUMBER_TEXT, new CaptchaTypeInfo("0123456789", false, new Font("Tahoma", Font.PLAIN, 40)));
        captchaTypeInfoMap.put(CaptchaType.ENGLISH_NUMBER, new CaptchaTypeInfo("0123456789", false, new Font("Tahoma", Font.PLAIN, 40)));
        captchaTypeInfoMap.put(CaptchaType.PERSIAN_CHAR, new CaptchaTypeInfo("بتجحخدرژسشضظعفقکلمنوهی", true, new Font("Tahoma", Font.PLAIN, 40)));
        captchaTypeInfoMap.put(CaptchaType.PERSIAN_NUMBER_CHAR, new CaptchaTypeInfo("0123456789بتجحخدرژسشضظعفقکلمنوهی", true, new Font("Tahoma", Font.PLAIN, 40)));
        captchaTypeInfoMap.put(CaptchaType.ARABIC_CHAR, new CaptchaTypeInfo("ابتثجحخدذرزسشصضطظفقعغکلمنوهی", true, new Font("Tahoma", Font.PLAIN, 40)));
        captchaTypeInfoMap.put(CaptchaType.ARABIC_NUMBER_CHAR, new CaptchaTypeInfo("0123456789ابتثجحخدذرزسشصضطظفقعغکلمنوهی", true, new Font("Tahoma", Font.PLAIN, 40)));
        captchaTypeInfoMap.put(CaptchaType.ENGLISH_CHAR, new CaptchaTypeInfo("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", false, new Font("Tahoma", Font.PLAIN, 40)));
        captchaTypeInfoMap.put(CaptchaType.ENGLISH_NUMBER_CHAR, new CaptchaTypeInfo("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", false, new Font("Tahoma", Font.PLAIN, 40)));
    }

    public GeneratedCaptcha generateCaptcha(CaptchaType type, int maxChars) throws IOException {
        CaptchaTypeInfo captchaTypeInfo = this.getCaptchaTypeInfoMap().get(type);
        if (null == captchaTypeInfo) {
            throw new IllegalStateException(String.format("no chars specify for type %s to generate captcha", type));
        }
        StringBuilder captchaText;
        if (StringUtils.isNumeric(captchaTypeInfo.getChars())) {
            captchaText = new StringBuilder(RandomStringUtils.random(maxChars, captchaTypeInfo.getChars()));
        } else {
            captchaText = this.removeContinuousNumbers(RandomStringUtils.random(maxChars, captchaTypeInfo.getChars()), captchaTypeInfo.getChars());
        }
        String text = captchaText.toString();
        String textForImage;
        if (type == CaptchaType.PERSIAN_NUMBER_TEXT) {
            textForImage = LetterConverterUtility.convertDigitToFarsiLetter(Long.valueOf(text));
        } else {
            textForImage = captchaTypeInfo.isRtl() ? captchaText.reverse().toString() : captchaText.toString();
        }
        return new GeneratedCaptcha(text, this.generateImage(type, textForImage, captchaTypeInfo.getFont()));
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

    public Map<CaptchaType, CaptchaTypeInfo> getCaptchaTypeInfoMap() {
        return captchaTypeInfoMap;
    }

    public void setCaptchaTypeInfoMap(Map<CaptchaType, CaptchaTypeInfo> captchaTypeInfoMap) {
        this.captchaTypeInfoMap = captchaTypeInfoMap;
    }
}
