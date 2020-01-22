package gra;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Gameplay {

    private List<Chicken> chickens = new CopyOnWriteArrayList<>();
    private List<Cloud> clouds = new CopyOnWriteArrayList<>();
    private Player player;
    private GameEngine engine;
    private Random random = new Random();
    private Background background;
    private HighScoresManager scoresManager;
    private int secondsPast;
    private int level = 1;


    public Gameplay(GameEngine engine, HighScoresManager scoresManager) {
        this.engine = engine;
        this.scoresManager = scoresManager;
        player = new Player();
        background = new Background();
        scoresManager.setActualPlayer(player);

    }

    public int randomizeHeight() {
        return random.nextInt(Display.getHeight()-100);
    }

    public void createClouds() {
        Cloud cloud = new Cloud(Display.getWidth()+200,randomizeHeight() );
        clouds.add(cloud);
    }

    public void createWhiteChickens() {

        Chicken chicken = new WhiteChicken(-100, randomizeHeight());
        chickens.add(chicken);

    }

    public void createWhiteChickensRL() {

        Chicken chicken = new WhiteChickenRL(Display.getWidth()+100, randomizeHeight());
        chickens.add(chicken);
    }

    public void createYellowChickens() {

        Chicken chicken = new YellowChicken(-100, randomizeHeight());
        chickens.add(chicken);

    }

    public void createYellowChickensRL() {

        Chicken chicken = new YellowChickenRL(Display.getWidth()+100, randomizeHeight());
        chickens.add(chicken);
    }

    public void createRedChickens() {

        Chicken chicken = new RedChicken(-100,randomizeHeight());
        chickens.add(chicken);

    }

    public void createRedChickensRL() {

        Chicken chicken = new RedChickenRL(Display.getWidth()+100,randomizeHeight());
        chickens.add(chicken);
    }


    public void playerClicked(Point point) {

        for (Chicken chicken : chickens) {
            if (chicken.isShoot(point)) {
                for (Cloud cloud : clouds) {
                    if (cloud.isShoot(point)) {
                        return;
                    }
                }
                chicken.decreaseLives();
            }
        }
    }

    private int redChickenFreq = 40;
    private int yellowChickFreq = 20;
    private int cloudsFreq = 3;
    private int whiteFreq = 2;

    public void secondTick() {
        secondsPast++;
        Random random = new Random();
        if (engine.getSeconds() % whiteFreq == 0) {
            if (random.nextBoolean()) {
                createWhiteChickens();
            } else {
                createWhiteChickensRL();
            }
        }
        if (engine.getSeconds() % cloudsFreq == 0) {
            createClouds();
        }

        if (engine.getSeconds() % yellowChickFreq == 0) {
            if (random.nextBoolean()) {
                createYellowChickens();
            } else {
                createYellowChickensRL();
            }
        }
        if (engine.getSeconds() % redChickenFreq == 0) {
            if (random.nextBoolean()) {
                createRedChickens();
            } else {
                createRedChickens();
            }
        }
        if (secondsPast % 10 == 0) {
            level++;
            System.out.println("Awans do poziomu: " + level + "! Punkty: " + player.getPoints());
            player.incLvl();
            redChickenFreq--;
            yellowChickFreq--;

        }
        if (redChickenFreq < 6) {
            redChickenFreq = 6;
            whiteFreq = 1;
        }
        if (yellowChickFreq < 3) {
            yellowChickFreq = 3;
            cloudsFreq = 2;
        }


//        if (engine.getSeconds()%15==0){
//            createClouds();
//        }
    }

    public void tick() {

        for (Chicken chicken : chickens) {
            chicken.tick();
        }
        for (Cloud cloud : clouds) {
            cloud.tick();
        }
        List<Chicken> chickensCoppy = new ArrayList<>(chickens);
        for (Chicken chicken : chickensCoppy) {
            if (!chicken.isKilled()) {
                chickens.remove(chicken);
                player.addPoints(chicken.getValue());
                continue;
            }
            if (chicken.reachedNextSide()) {
                chickens.remove(chicken);
                player.reduceLives();
                continue;
            }
        }
        if (!player.isAlive()) {
            gameEnded();
        }


    }

    public void render(Graphics graphics) {

        background.render(graphics);

        for (Chicken chicken : chickens) {
            chicken.render(graphics);
        }
        for (Cloud cloud : clouds) {
            cloud.render(graphics);
        }

    }

    public void gameEnded() {
        engine.gameEnded();
    }


}
