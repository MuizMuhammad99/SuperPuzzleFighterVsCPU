package com.game.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Loads image from the class folder
 */
public class ImageLoader {

    public static BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(ImageLoader.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
