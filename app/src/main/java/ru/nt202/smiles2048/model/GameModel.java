package ru.nt202.smiles2048.model;

import java.util.ArrayList;

public class GameModel {
    private static final GameModel ourInstance = new GameModel();

    private static GameField gameField;
    private ArrayList<Smile> smiles;

    public static GameModel getInstance() {
        return ourInstance;
    }

    private GameModel() {
        gameField = new GameField();
    }

    public void updateModel(final Direction direction) {
        smiles = gameField.move(direction);
    }

    public ArrayList<Smile> getSmiles() {
        return smiles;
    }
}
