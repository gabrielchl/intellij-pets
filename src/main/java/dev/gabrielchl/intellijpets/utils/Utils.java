package dev.gabrielchl.intellijpets.utils;

import com.intellij.util.ui.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Utils {
    public static Map<String, Integer> petToSpriteSize = Map.ofEntries(
            Map.entry("axolotl", 32),
            Map.entry("bunny", 40),
            Map.entry("cat-1", 40),
            Map.entry("cat-2", 40),
            Map.entry("cat-3", 40),
            Map.entry("cat-4", 40),
            Map.entry("cat-5", 40),
            Map.entry("chicken", 40),
            Map.entry("dog-1", 32),
            Map.entry("dog-2", 32),
            Map.entry("dog-3", 32),
            Map.entry("dog-4", 32),
            Map.entry("dog-5", 32),
            Map.entry("hedgehog", 32),
            Map.entry("trill", 40)
    );

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
