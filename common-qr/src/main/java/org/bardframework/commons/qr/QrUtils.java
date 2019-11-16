package org.bardframework.commons.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.bardframework.commons.util.AssertionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class QrUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(QrUtils.class);

    private QrUtils() {
    }

    public static byte[] getQR(String text)
            throws WriterException, IOException {
        AssertionUtils.hasText(text, "null or empty text not acceptable");
        int scale = 16;
        Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hintMap.put(EncodeHintType.DATA_MATRIX_SHAPE, SymbolShapeHint.FORCE_SQUARE);
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hintMap.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 0, 0, hintMap);
        BufferedImage image = new BufferedImage(bitMatrix.getWidth() * scale, bitMatrix.getHeight() * scale, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < bitMatrix.getWidth(); i++) {
            for (int j = 0; j < bitMatrix.getHeight(); j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i * scale, j * scale, scale, scale);
                }
            }
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

    public static byte[] getQR(String text, String authenticationCode)
            throws IOException {
        AssertionUtils.hasText(text, "null or empty text not acceptable");
        AssertionUtils.hasText(authenticationCode, "null or empty authentication code not acceptable");
        int scale = 16;
        int footerHeight = 8;
        Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hintMap.put(EncodeHintType.DATA_MATRIX_SHAPE, SymbolShapeHint.FORCE_SQUARE);
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hintMap.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 0, 0, hintMap);
        } catch (WriterException e) {
            LOGGER.error("error encoding data as qr.", e);
            throw new IOException("error encoding data as qr.", e);
        }
        BufferedImage image = new BufferedImage(bitMatrix.getWidth() * scale, bitMatrix.getHeight() * scale + (scale * footerHeight), BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < bitMatrix.getWidth(); i++) {
            for (int j = 0; j < bitMatrix.getHeight(); j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i * scale, j * scale, scale, scale);
                }
            }
        }
        double rate = 100;
        int authenticationCodeWidth = (int) Math.ceil(authenticationCode.length() * rate * 0.5 * scale);
        while (authenticationCodeWidth > image.getWidth()) {
            authenticationCodeWidth = (int) Math.ceil(authenticationCode.length() * rate-- * 0.5 * scale);
        }
        int fontSize = (int) (rate * scale * 1.0);
        graphics.setFont(new Font("B Yekan", Font.PLAIN, fontSize));
        graphics.drawString(authenticationCode, (image.getWidth() - authenticationCodeWidth) / 2, image.getHeight() - (scale * 2));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
