package gra;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background {
    private BufferedImage image;

    public Background() {
        image = ImageLoader.loadImage("/GameBackground.png");
    }

    public void render (Graphics graphics){
        graphics.drawImage(image,0,0,Display.getWidth(),Display.getHeight(),null);

    }
}
