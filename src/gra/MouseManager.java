package gra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseManager implements MouseListener {

    private Display display;
    private Gameplay gameplay;

    public MouseManager(Display display, Gameplay gameplay) {
        this.display = display;
        this.gameplay = gameplay;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
       new SwingWorker<Void,Void>() {
           @Override
           protected Void doInBackground() throws Exception {
               Point point = e.getLocationOnScreen();
               SwingUtilities.convertPointFromScreen(point, display.getCanvas());
               gameplay.playerClicked(point);
               return null;
           }
       }.execute();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
