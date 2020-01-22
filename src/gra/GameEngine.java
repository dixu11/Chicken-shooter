package gra;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameEngine implements Runnable {

    private Display display;

    private Thread thread;
    private boolean running;
    private BufferStrategy strategy;

    private Gameplay gameplay;

    private ViewFactory factory;
    private int seconds;

    public GameEngine(ViewFactory factory) {
        this.factory = factory;
        running = false;
        display = new Display("Game", new Dimension(626*2, 352*2));
        gameplay = new Gameplay(this, factory.getScoresManager());
        MouseManager mouseManager = new MouseManager(display, gameplay);
        display.registerMouseManager(mouseManager);
        display.setWisibility(true);


    }


    @Override
    public void run() {
        int fps = 60;
        double timePerTick = 1_000_000_000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta>= 1) {
                executeGameLogic(ticks);
                render();
                ticks++;
                delta--;

            }

            if (timer>=1_000_000_000) {
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }

    private void executeGameLogic(int ticks) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                updateSeconds(ticks);
                frameTick();
                return null;
            }
        }.execute();
    }

    private void updateSeconds(int ticks) {
        if(ticks == 1){
            seconds++;
            gameplay.secondTick();
        }
    }

// ticks every second



//tick every fps
    private void frameTick() {
        gameplay.tick();//metoda odpowiedzialna za całą logikę gry
    }

    private void render() {
        if (!ensureBufferReady()) {//wyswietla obiekty na ekranie
            return;
        }
        renderFrame();

    }

    private boolean ensureBufferReady() {
        Canvas canvas = display.getCanvas();
        strategy = canvas.getBufferStrategy();//przygotowuje kolejkę klatek gry

        if (strategy == null) {
            canvas.createBufferStrategy(3);
            return false;
        }
        return true;
    }

    private void renderFrame() {
        Graphics graphics = strategy.getDrawGraphics();//przygotowujemy obiekt Graphics posłuży jako pędzel malujący rozgryfkę

        graphics.clearRect(0,0,Display.getWidth(),Display.getHeight());         //clear screen

       //DRAW HERE
        gameplay.render(graphics);
        // FINALISING
        strategy.show();
        graphics.dispose();
    }

    public synchronized void start() {
        if (running) {
            return;
        }

        running = true;
        thread = new Thread(this);//wątkowi ustawiamy jako obiekt z metodą run włanie tą klasę w której jestemy
        thread.start();//metoda start na wątku wywołuje metodę run przekazanego obiektu
    }

    private synchronized void stop() {
        if (!running) {
            return;
        }

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setVisible(boolean value){
        display.setWisibility(value);
    }

    public void gameEnded(){
        running = false;
        display.dispose();
        factory.gameEnded();
    }

    public int getSeconds() {
        return seconds;
    }
}
