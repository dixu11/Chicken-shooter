package gra;

import java.io.Serializable;

public class Player implements Serializable,Comparable<Player> {
    private int lives = 10;
    private int score;
    private String name ="";
    private int maxLvl = 1;

    public void addPoints(int points){
        score+=points;

    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " punkty: " + score + " poziom: " + maxLvl;
    }

    @Override
    public int compareTo(Player o) {
        return Integer.compare(o.score,score);
    }

    public boolean isAlive() {
        return lives != 0;
    }

    public void reduceLives() {
        lives--;
    }

    public int getPoints() {
        return score;
    }

    public void incLvl() {
        maxLvl++;
    }
}
