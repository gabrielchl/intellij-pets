package dev.gabrielchl.intellijpets.utils;

import com.intellij.util.ui.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Utils {
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bufferedImg = ImageUtil.createImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bufferedImg.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bufferedImg;
    }

    public static void waitForImageToLoad(Image img) {
        JLabel dummy = new JLabel();
        MediaTracker mt = new MediaTracker(dummy);
        mt.addImage(img, 0);
        try {
            mt.waitForID(0);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }
}
