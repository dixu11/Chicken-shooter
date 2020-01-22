package gra;


import javax.swing.*;


public class Launcher {
    public static void main(String[] args) {
//        GameEngine gameEngine = new GameEngine("Chicken",new Dimension(800,600));
//        gameEngine.start();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ViewFactory();
            }
        });

    }
}
