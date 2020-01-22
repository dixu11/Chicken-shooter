package gra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ViewFactory {
    private JFrame menuFrame;
    private JList <Player> players;
    private JButton startButton;
    private JPanel panel;
    DefaultListModel<Player> playersModel;

    private JButton exitButton;
    private GameEngine gameEngine;
    private HighScoresManager scoresManager;

    public ViewFactory() {

        menuFrame = new JFrame("Menu");
        menuFrame.setSize(200,400);
        //menuFrame.setLocationRelativeTo(null);

        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startButton = new JButton("New Game");
        panel = new JPanel(new BorderLayout());
        panel.add(startButton,BorderLayout.NORTH);
        menuFrame.add(panel);


        startButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                initializeGame();
                gameEngine.start();
            }
        });
        exitButton = new JButton("EXIT");
        panel.add(exitButton,BorderLayout.SOUTH);
        menuFrame.add(panel);
        scoresManager = new HighScoresManager();
        initializeHS();
        menuFrame.setVisible(true);
    }

    private void initializeGame(){
        gameEngine = new GameEngine(this);

    }

    private void initializeHS (){
        playersModel = new DefaultListModel<>();
        players = new JList<>();
        players.setModel(playersModel);
        players.setVisibleRowCount(10);
        players.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(players));
       loadScores();
    }

    private void loadScores() {
        playersModel.removeAllElements();
        playersModel.addAll(scoresManager.getBestPlayers());
    }

    public void gameEnded(){
        getScoreRecord();
        menuFrame.setVisible(true);
    }
    public void getScoreRecord(){
        String name = JOptionPane.showInputDialog("Gra zakończona! Jak się nazywasz?");//jeśli nikt nic nie wpisał mamy pusty String
        //Zamknięcie okna i cansel zapisuje Null
        scoresManager.saveNewRecord(name);
        loadScores();
    }

    public HighScoresManager getScoresManager() {
        return scoresManager;
    }
}
