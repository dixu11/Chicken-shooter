package gra;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Chicken extends FlyingObject implements GameObject {

    private int lives;
    private int value;

    public Chicken(int x, int y, int width, int height, int speed,int lives, int value, String imagePath) {
        super(x, y, width, height, speed, imagePath);
        this.lives = lives;
        this.value = value;
    }

    public synchronized void decreaseLives(){

        lives--;
    }

    @Override
    protected boolean isKilled() {
        return lives != 0;
    }

    public int getValue() {
        return value;
    }
}
