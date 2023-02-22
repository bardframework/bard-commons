package org.bardframework.commons.utils;

import lombok.experimental.UtilityClass;

import java.awt.*;
import java.awt.image.BufferedImage;

@UtilityClass
public class ImageUtils {

    /**
     * resize image, keep aspect ratio
     *
     * @return resized image as BufferedImage
     */
    public static BufferedImage resize(BufferedImage image, int width) {
        return resize(image, width, (image.getHeight() * width) / image.getWidth());
    }

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(image, 0, 0, width, height, null);
        graphics.dispose();
        return resizedImage;
    }
}
