package gra;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class FlyingObject implements GameObject {

    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private BufferedImage image;
    private int distance;

    public FlyingObject(int x, int y, int width, int height, int speed, String imagePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        image = ImageLoader.loadImage(imagePath);
    }

    boolean isOnScreen(){
        return x > Display.getWidth() || x<0;
    }

    @Override
    public void tick() {
        x+=speed;
        distance += Math.abs(speed);
    }

    @Override
    public void render(Graphics graphics) {

        if (!isKilled()){
            return;
        }

        graphics.drawImage(image,x,y,width,height,null);

    }

    protected abstract boolean isKilled();


    boolean isShoot(Point point){

        Rectangle rectangle = new Rectangle(x,y,width,height);

        if(rectangle.contains(point)){

            return true;
        }
        else {
            return false;
        }
    }

    public boolean reachedNextSide() {
        return distance > Display.getWidth() + width*2;
    }

}
