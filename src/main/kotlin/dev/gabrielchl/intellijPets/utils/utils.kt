package dev.gabrielchl.intellijPets.utils

import com.intellij.util.ui.ImageUtil
import java.awt.Image
import java.awt.MediaTracker
import java.awt.image.BufferedImage
import javax.swing.JLabel

object Utilities {
    fun waitForImageToLoad(img: Image) {
        val dummy = JLabel()
        val mt = MediaTracker(dummy)
        mt.addImage(img, 0)
        try {
            mt.waitForID(0)
        } catch (e2: InterruptedException) {
            e2.printStackTrace()
        }
    }

    fun toBufferedImage(img: Image): BufferedImage {
        if (img is BufferedImage) {
            return img
        }

        val bufferedImg = ImageUtil.createImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB)
        val bGr = bufferedImg.createGraphics()
        bGr.drawImage(img, 0, 0, null)
        bGr.dispose()

        return bufferedImg
    }
}