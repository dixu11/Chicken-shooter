package gra;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HighScoresManager {
    private List<Player> bestPlayers;
    private Player actualPlayer;

    public HighScoresManager() {
        loadBestPlayers();
    }

    private void loadBestPlayers(){
        File archive = new File("Archive.bin");
        try {
            FileInputStream fis = new FileInputStream(archive);
            ObjectInputStream ois = new ObjectInputStream(fis);
            bestPlayers = (List<Player>) ois.readObject();
        } catch (FileNotFoundException e) {
            bestPlayers = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
    public boolean saveNewRecord(String name){
        if (name==null || name.isEmpty()){
            return false;
        }
        actualPlayer.setName(name);
        bestPlayers.add(actualPlayer);
        saveAllPlayers();
        return true;
    }

    private void saveAllPlayers(){
        File archive = new File("Archive.bin");
        try {
            FileOutputStream fos = new FileOutputStream(archive);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(bestPlayers);
            oos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setActualPlayer(Player actualPlayer) {
        this.actualPlayer = actualPlayer;
    }

    //returns sorted version
    public List<Player> getBestPlayers() {
        List<Player> sorted = new ArrayList<>(bestPlayers);
        Collections.sort(sorted);
        return sorted;
    }
}
