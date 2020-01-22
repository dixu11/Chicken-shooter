package gra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Display {

    private static int width;
    private static int height;

    private JFrame frame;
    private Canvas canvas;

    private String title;
    private Dimension size;

    public Display(String title, Dimension size) {
        this.title = title;
        this.size = size;
        width = size.width;
        height = size.height;

        createAndSetupFrame();
        createAndSetupCanvas();
        frame.pack();
    }

    private void createAndSetupFrame() {
        frame = new JFrame(title);

        frame.setSize(size);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

    }

    private void createAndSetupCanvas() {
        canvas = new Canvas();

        canvas.setPreferredSize(size);
        canvas.setMaximumSize(size);
        canvas.setMinimumSize(size);
        frame.add(canvas);

        canvas.setFocusable(true);
        canvas.requestFocusInWindow();
    }


    public Canvas getCanvas() {
        return canvas;
    }

    public static int getHeight() {
        return height;
    }

    public static int getWidth() {
        return width;
    }

    public void registerMouseManager(MouseListener mouseListener){
        canvas.addMouseListener(mouseListener);
    }

    public void setWisibility(boolean value){
        frame.setVisible(value);
    }

    public void dispose(){
        frame.dispose();
    }

}
